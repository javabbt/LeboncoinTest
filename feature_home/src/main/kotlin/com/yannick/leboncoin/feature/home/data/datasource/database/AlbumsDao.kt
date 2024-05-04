package com.yannick.leboncoin.feature.home.data.datasource.database

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.yannick.leboncoin.feature.home.data.datasource.database.model.AlbumEntityModel

@Dao
interface AlbumsDao {
    @Query("SELECT * FROM albums")
    suspend fun getAlbums(): List<AlbumEntityModel>

    @Upsert
    suspend fun upsertAll(albums: List<AlbumEntityModel>)
}
