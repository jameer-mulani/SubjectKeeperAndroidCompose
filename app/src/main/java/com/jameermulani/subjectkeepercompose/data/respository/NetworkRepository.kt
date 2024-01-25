package com.jameermulani.subjectkeepercompose.data.respository

import com.jameermulani.subjectkeepercompose.data.model.SearchedImage
import com.jameermulani.subjectkeepercompose.data.model.SearchedImageResponse
import com.jameermulani.subjectkeepercompose.data.source.remote.RemoteApi
import retrofit2.HttpException
import java.net.ConnectException
import java.net.UnknownHostException
import javax.inject.Inject

class NetworkRepositoryImpl @Inject constructor(
    private val remoteApi: RemoteApi
) {

    suspend fun searchImage(search: String): SearchedImageResponse {
        try {
            val response = remoteApi.searchImage(search)
            if (response.isSuccessful) {
                val body = response.body() ?: return SearchedImageResponse(
                    results = listOf(),
                    error = "No data",
                    success = true
                )
                val results = body.hits.map {
                    SearchedImage(it.previewURL)
                }
                return SearchedImageResponse(results = results, error = null, success = true)
            } else {
                return SearchedImageResponse(
                    results = null,
                    error = "Failed to query",
                    success = false
                )
            }

        } catch (e: Exception) {
            when (e) {
                is HttpException,
                is UnknownHostException,
                is ConnectException -> {
                    return SearchedImageResponse(
                        results = null,
                        error = "No internet",
                        success = false
                    )
                }

                else -> {
                    return SearchedImageResponse(
                        results = null,
                        error = "Something went wrong, ${e.message.toString()}",
                        success = false
                    )
                }
            }
        }
    }
}