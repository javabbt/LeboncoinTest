package com.yannick.leboncoin.feature.home.data

import androidx.room.Room
import com.yannick.leboncoin.feature.home.data.datasource.api.service.LeboncoinApi
import com.yannick.leboncoin.feature.home.data.datasource.database.AlbumsDatabase
import com.yannick.leboncoin.feature.home.data.repository.AlbumsRepositoryImpl
import com.yannick.leboncoin.feature.home.domain.repository.AlbumsRepository
import org.koin.dsl.module
import retrofit2.Retrofit

internal val dataModule = module {
    single { get<Retrofit>().create(LeboncoinApi::class.java) }
    single<AlbumsRepository> { AlbumsRepositoryImpl(get(), get()) }
    single {
        Room.databaseBuilder(
            get(),
            AlbumsDatabase::class.java,
            "Albums.db",
        ).build()
    }
    single { get<AlbumsDatabase>().albumsDao() }
}
