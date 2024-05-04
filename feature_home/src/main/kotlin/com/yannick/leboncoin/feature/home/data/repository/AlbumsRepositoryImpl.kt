package com.yannick.leboncoin.feature.home.data.repository

import com.leboncoin.resources.R
import com.yannick.leboncoin.base.data.retrofit.ApiResult
import com.yannick.leboncoin.base.utils.CoroutinesDispatcherProvider
import com.yannick.leboncoin.feature.home.data.datasource.api.response.toAlbumEntityModel
import com.yannick.leboncoin.feature.home.data.datasource.api.response.toAlbumUiModel
import com.yannick.leboncoin.feature.home.data.datasource.api.service.LeboncoinApi
import com.yannick.leboncoin.feature.home.data.datasource.database.AlbumsDatabase
import com.yannick.leboncoin.feature.home.data.datasource.database.model.toDomainModel
import com.yannick.leboncoin.feature.home.domain.model.AlbumUiModel
import com.yannick.leboncoin.feature.home.domain.repository.AlbumsRepository
import com.yannick.leboncoin.feature.home.domain.utils.Result
import kotlinx.coroutines.withContext

class AlbumsRepositoryImpl(
    private val leboncoinApi: LeboncoinApi,
    private val albumsDatabase: AlbumsDatabase,
) : AlbumsRepository {
    override suspend fun getAlbums(): Result<List<AlbumUiModel>> {
        return when (val result = leboncoinApi.getAlbums()) {
            is ApiResult.Success -> {
                albumsDatabase.albumsDao().upsertAll(result.data.map { it.toAlbumEntityModel() })
                Result.Success(result.data.map { it.toAlbumUiModel() })
            }

            is ApiResult.Error -> {
                val localData = albumsDatabase.albumsDao().getAlbums()
                if (localData.isNotEmpty()) {
                    Result.Success(localData.map { it.toDomainModel() })
                } else {
                    result.message?.let {
                        Result.Error(it)
                    } ?: Result.UnexpectedError(R.string.unexpected_error)
                }
            }

            is ApiResult.Exception -> {
                val localData = albumsDatabase.albumsDao().getAlbums()
                if (localData.isNotEmpty()) {
                    Result.Success(localData.map { it.toDomainModel() })
                } else {
                    result.throwable.message?.let {
                        Result.Error(it)
                    } ?: Result.UnexpectedError(R.string.unexpected_error)
                }
            }
        }
    }
}
