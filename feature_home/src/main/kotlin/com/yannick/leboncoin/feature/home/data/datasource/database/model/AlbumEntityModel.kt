package com.yannick.leboncoin.feature.home.data.datasource.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.yannick.leboncoin.feature.home.domain.model.AlbumUiModel

@Entity(tableName = "albums")
data class AlbumEntityModel(
    val albumId: Int,
    @PrimaryKey
    val id: Int,
    val title: String,
    val url: String,
    val thumbnailUrl: String,
)

fun AlbumEntityModel.toDomainModel() = AlbumUiModel(
    albumId = albumId,
    id = id,
    title = title,
    url = url,
    thumbnailUrl = thumbnailUrl,
)
