package com.yannick.leboncoin.feature.home.domain.usecases

import com.yannick.leboncoin.feature.home.domain.model.AlbumUiModel
import com.yannick.leboncoin.feature.home.domain.repository.AlbumsRepository
import com.yannick.leboncoin.feature.home.domain.utils.Result
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class GetAlbumsUseCaseTest {

    private val mockRepository: AlbumsRepository = mockk()

    private val useCase = GetAlbumsUseCase(mockRepository)

    @Test
    fun `invoke returns Success when repository call is successful`() = runBlocking {
        // Given
        val mockResult = Result.Success(listOf<AlbumUiModel>(mockk()))
        coEvery { mockRepository.getAlbums() } returns mockResult

        // When
        val result = useCase()

        // Then
        assertTrue(result is Result.Success)
    }

    @Test
    fun `invoke returns Error when repository call fails`() = runBlocking {
        // Given
        val mockResult = Result.Error("Error")
        coEvery { mockRepository.getAlbums() } returns mockResult

        // When
        val result = useCase()

        // Then
        assertTrue(result is Result.Error)
    }

    @Test
    fun `invoke returns UnexpectedError when repository call throws an exception`() = runBlocking {
        // Given
        val mockResult = Result.UnexpectedError(0)
        coEvery { mockRepository.getAlbums() } returns mockResult

        // When
        val result = useCase()

        // Then
        assertTrue(result is Result.UnexpectedError)
    }
}
