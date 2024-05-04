package com.yannick.leboncoin.feature.home.domain.usecases

import com.yannick.leboncoin.feature.home.domain.repository.AlbumsRepository

class GetAlbumsUseCase(
    private val albumsRepository: AlbumsRepository,
) {
    suspend operator fun invoke() = albumsRepository.getAlbums()
}
