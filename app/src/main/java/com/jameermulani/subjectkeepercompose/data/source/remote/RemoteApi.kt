package com.jameermulani.subjectkeepercompose.data.source.remote

import com.jameermulani.subjectkeepercompose.data.source.remote.dto.ImageResponse
import com.jameermulani.subjectkeepercompose.util.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface RemoteApi {

    @GET("/api/")
    suspend fun searchImage(
        @Query("q") search: String,
        @Query("key") apiKey: String = Constants.API_KEY
    ): Response<ImageResponse>


}