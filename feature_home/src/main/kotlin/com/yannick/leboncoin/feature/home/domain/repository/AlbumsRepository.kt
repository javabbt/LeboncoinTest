package com.yannick.leboncoin.feature.home.domain.repository

import com.yannick.leboncoin.feature.home.domain.model.AlbumUiModel
import com.yannick.leboncoin.feature.home.domain.utils.Result

interface AlbumsRepository {
    suspend fun getAlbums(): Result<List<AlbumUiModel>>
}
