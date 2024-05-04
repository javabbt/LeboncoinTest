package com.yannick.leboncoin.feature.home.data.datasource.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.yannick.leboncoin.feature.home.data.datasource.database.model.AlbumEntityModel

@Database(entities = [AlbumEntityModel::class], version = 1)
abstract class AlbumsDatabase : RoomDatabase() {
    abstract fun albumsDao(): AlbumsDao
}
