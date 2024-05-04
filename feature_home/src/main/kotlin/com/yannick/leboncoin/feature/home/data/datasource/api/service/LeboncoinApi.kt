package com.yannick.leboncoin.feature.home.data.datasource.api.service

import com.yannick.leboncoin.base.data.retrofit.ApiResult
import com.yannick.leboncoin.feature.home.data.datasource.api.response.ApiResponse
import retrofit2.http.GET

interface LeboncoinApi {
    @GET("technical-test.json")
    suspend fun getAlbums(): ApiResult<List<ApiResponse>>
}
