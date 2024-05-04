import androidx.lifecycle.SavedStateHandle
import com.google.common.truth.Truth.assertThat
import com.yannick.leboncoin.base.test.provideFakeCoroutinesDispatcherProvider
import com.yannick.leboncoin.feature.home.domain.usecases.GetAlbumsUseCase
import com.yannick.leboncoin.feature.home.domain.utils.Result
import com.yannick.leboncoin.feature.home.presentation.screen.home.HomeScreenViewModel
import com.yannick.leboncoin.feature.home.presentation.screen.home.SideEffect
import com.yannick.leboncoin.feature.testData
import com.yannick.leboncoin.library.testutils.MainCoroutineRule
import com.yannick.leboncoin.library.testutils.runTest
import com.yannick.leboncoin.library.testutils.test
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.Rule
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@ExperimentalCoroutinesApi
class HomeScreenViewModelTest {

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var viewModel: HomeScreenViewModel
    private val getAlbumsUseCase = mockk<GetAlbumsUseCase>()
    private val savedStateHandle = mockk<SavedStateHandle>(relaxed = true)
    private val dispatcherProvider =
        provideFakeCoroutinesDispatcherProvider(mainCoroutineRule.testDispatcher)
    private val testDispatcher = StandardTestDispatcher()

    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        viewModel = HomeScreenViewModel(savedStateHandle, getAlbumsUseCase, dispatcherProvider)
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `initial load executes onSearch with successful result`() = mainCoroutineRule.runTest {
        val fakeAlbums = listOf(testData)
        coEvery { getAlbumsUseCase() } returns Result.Success(fakeAlbums)

        val viewState = viewModel.uiState.test(this)

        viewModel.onSearch()

        assertThat(viewState[0].isLoading).isTrue()
        assertThat(viewState[0].filteredAlbums).isEmpty()
        assertThat(viewState[0].albums).isEmpty()
        assertThat(viewState[0].query).isEmpty()

        assertThat(viewState[1].isLoading).isFalse()
        assertThat(viewState[1].filteredAlbums[0]).isEqualTo(testData)
        assertThat(viewState[1].albums[0]).isEqualTo(testData)
        assertThat(viewState[1].query).isEmpty()

        viewState.finish()
    }

    @Test
    fun `onSearch handles errors correctly`() = mainCoroutineRule.runTest {
        coEvery { getAlbumsUseCase() } returns Result.Error("Error occurred")

        val sideEffect = viewModel.sideEffects.test(this)
        val viewState = viewModel.uiState.test(this)
        viewModel.onSearch()

        assertThat(viewState[0].isLoading).isTrue()
        assertThat(viewState[0].filteredAlbums).isEmpty()
        assertThat(viewState[0].albums).isEmpty()
        assertThat(viewState[0].query).isEmpty()

        assertThat(sideEffect[0] is SideEffect.ShowError).isTrue()
        assertThat((sideEffect[0] as SideEffect.ShowError).msg).isEqualTo("Error occurred")

        sideEffect.finish()
        viewState.finish()
    }

    @Test
    fun `onSearch handles exceptions correctly`() = mainCoroutineRule.runTest {
        val exceptionCode = 1
        coEvery { getAlbumsUseCase() } returns Result.UnexpectedError(exceptionCode)

        val sideEffect = viewModel.sideEffects.test(this)
        val viewState = viewModel.uiState.test(this)
        viewModel.onSearch()

        assertThat(viewState[0].isLoading).isTrue()
        assertThat(viewState[0].filteredAlbums).isEmpty()
        assertThat(viewState[0].albums).isEmpty()
        assertThat(viewState[0].query).isEmpty()

        assertThat(sideEffect[0] is SideEffect.ShowUnexpectedError).isTrue()
        assertThat((sideEffect[0] as SideEffect.ShowUnexpectedError).msg).isEqualTo(exceptionCode)

        sideEffect.finish()
        viewState.finish()
    }
}
