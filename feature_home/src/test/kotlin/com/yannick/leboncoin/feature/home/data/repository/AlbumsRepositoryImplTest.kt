package com.yannick.leboncoin.feature.home.data.repository

import com.yannick.leboncoin.base.data.retrofit.ApiResult
import com.yannick.leboncoin.base.test.provideFakeCoroutinesDispatcherProvider
import com.yannick.leboncoin.base.utils.CoroutinesDispatcherProvider
import com.yannick.leboncoin.feature.home.data.datasource.api.response.ApiResponse
import com.yannick.leboncoin.feature.home.data.datasource.api.response.toAlbumEntityModel
import com.yannick.leboncoin.feature.home.data.datasource.api.service.LeboncoinApi
import com.yannick.leboncoin.feature.home.data.datasource.database.AlbumsDatabase
import com.yannick.leboncoin.feature.home.data.datasource.database.model.AlbumEntityModel
import com.yannick.leboncoin.feature.home.data.datasource.database.model.toDomainModel
import com.yannick.leboncoin.feature.home.domain.utils.Result
import com.yannick.leboncoin.library.testutils.MainCoroutineRule
import com.yannick.leboncoin.library.testutils.runTest
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@ExperimentalCoroutinesApi
class AlbumsRepositoryImplTest {

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private val mockApi: LeboncoinApi = mockk(relaxed = true, relaxUnitFun = true)
    private val mockDatabase: AlbumsDatabase = mockk(relaxed = true, relaxUnitFun = true)

    private lateinit var sut: AlbumsRepositoryImpl

    @BeforeEach
    fun setUp() {
        sut = AlbumsRepositoryImpl(mockApi, mockDatabase)
    }

    @Test
    fun `getAlbums returns Success from API`() = mainCoroutineRule.runTest {
        val mockApiData = listOf(mockk<ApiResponse>(relaxed = true))
        coEvery { mockApi.getAlbums() } returns ApiResult.Success(mockApiData)
        coEvery { mockDatabase.albumsDao().upsertAll(any()) } returns Unit
        coEvery {
            mockDatabase.albumsDao().getAlbums()
        } returns mockApiData.map { it.toAlbumEntityModel() }

        val result = sut.getAlbums()

        assert(result is Result.Success)
        coVerify { mockApi.getAlbums() }
        coVerify { mockDatabase.albumsDao().upsertAll(any()) }
    }

    @Test
    fun `getAlbums handles ApiResult Error with local data fallback`() = mainCoroutineRule.runTest {
        val localData = listOf(mockk<AlbumEntityModel>(relaxed = true))
        coEvery { mockApi.getAlbums() } returns ApiResult.Error(1, "Network Error")
        coEvery { mockDatabase.albumsDao().getAlbums() } returns localData

        val result = sut.getAlbums()

        assert(result is Result.Success)
        Assertions.assertEquals(
            localData.map { it.toDomainModel() },
            (result as Result.Success).output,
        )
    }

    @Test
    fun `getAlbums handles ApiResult Error when no local data`() = mainCoroutineRule.runTest {
        coEvery { mockApi.getAlbums() } returns ApiResult.Error(1, "Network Error")
        coEvery { mockDatabase.albumsDao().getAlbums() } returns emptyList()

        val result = sut.getAlbums()

        assert(result is Result.Error)
        Assertions.assertEquals("Network Error", (result as Result.Error).exception)
    }

    @Test
    fun `getAlbums handles ApiResult Exception with local data fallback`() =
        mainCoroutineRule.runTest {
            val localData = listOf(mockk<AlbumEntityModel>(relaxed = true))
            coEvery { mockApi.getAlbums() } returns ApiResult.Exception(RuntimeException("Network Failure"))
            coEvery { mockDatabase.albumsDao().getAlbums() } returns localData

            val result = sut.getAlbums()

            assert(result is Result.Success)
            Assertions.assertEquals(
                localData.map { it.toDomainModel() },
                (result as Result.Success).output,
            )
        }

    @Test
    fun `getAlbums handles ApiResult Exception when no local data`() = mainCoroutineRule.runTest {
        val errorMessage = "Network Failure"
        coEvery { mockApi.getAlbums() } returns ApiResult.Exception(RuntimeException(errorMessage))
        coEvery { mockDatabase.albumsDao().getAlbums() } returns emptyList()

        val result = sut.getAlbums()

        assert(result is Result.Error)
        Assertions.assertTrue((result as Result.Error).exception == errorMessage)
    }
}
