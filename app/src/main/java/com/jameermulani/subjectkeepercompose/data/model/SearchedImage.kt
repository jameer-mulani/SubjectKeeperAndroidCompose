package com.jameermulani.subjectkeepercompose.data.model

import coil.compose.AsyncImagePainter

data class SearchedImage(
    val previewUrl: String
)

data class SearchedImageResponse(
    val results: List<SearchedImage>? = null,
    val error: String? = null,
    val success: Boolean = false
)