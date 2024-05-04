package com.yannick.leboncoin.base

import com.yannick.leboncoin.base.constants.BASE_URL
import com.yannick.leboncoin.base.data.retrofit.ApiResultAdapterFactory
import com.yannick.leboncoin.base.utils.CoroutinesDispatcherProvider
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber

val baseModule = module {
    single {
        HttpLoggingInterceptor { message ->
            Timber.d("Http: $message")
        }.apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }
    single {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(ApiResultAdapterFactory())
            .client(
                OkHttpClient.Builder()
                    .addInterceptor(get<HttpLoggingInterceptor>())
                    .build(),
            )
            .build()
    }
    single {
        CoroutinesDispatcherProvider(Dispatchers.Main, Dispatchers.Default, Dispatchers.IO)
    }
}
