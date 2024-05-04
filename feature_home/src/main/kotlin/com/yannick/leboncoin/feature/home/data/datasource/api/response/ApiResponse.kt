package com.yannick.leboncoin.feature.home.data.datasource.api.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.yannick.leboncoin.feature.home.data.datasource.database.model.AlbumEntityModel
import com.yannick.leboncoin.feature.home.domain.model.AlbumUiModel
import kotlinx.parcelize.Parcelize

@Parcelize
data class ApiResponse(
    @field:SerializedName("albumId")
    val albumId: Int,

    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("title")
    val title: String,

    @field:SerializedName("url")
    val url: String,

    @field:SerializedName("thumbnailUrl")
    val thumbnailUrl: String,
) : Parcelable

fun ApiResponse.toAlbumUiModel(): AlbumUiModel {
    return AlbumUiModel(
        albumId = albumId,
        id = id,
        title = title,
        url = url,
        thumbnailUrl = thumbnailUrl,
    )
}

fun ApiResponse.toAlbumEntityModel(): AlbumEntityModel {
    return AlbumEntityModel(
        albumId = albumId,
        id = id,
        title = title,
        url = url,
        thumbnailUrl = thumbnailUrl,
    )
}
