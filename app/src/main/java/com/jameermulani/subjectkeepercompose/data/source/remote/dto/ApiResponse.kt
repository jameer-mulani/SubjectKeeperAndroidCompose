package com.jameermulani.subjectkeepercompose.data.source.remote.dto

data class ImageResponse(
    val hits: List<ImageResult>,
    val total: Int,
    val totalHits: Int
)

data class ImageResult(
    val previewURL: String,
    val largeImageURL: String,
)