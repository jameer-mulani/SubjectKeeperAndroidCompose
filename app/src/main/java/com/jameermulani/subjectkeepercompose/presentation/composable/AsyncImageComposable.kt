package com.jameermulani.subjectkeepercompose.presentation.composable

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.jameermulani.subjectkeepercompose.R

@Composable
fun AsyncImageComposable(
    url: String,
    modifier: Modifier = Modifier,
    enableCrossFade: Boolean = true,
    @DrawableRes placeHolderImage: Int = R.drawable.ic_launcher_foreground,
    contentDescription: String = "image",
    contentScale: ContentScale = ContentScale.Crop,
) {
    AsyncImage(
        model = ImageRequest
            .Builder(LocalContext.current)
            .data(url)
            .crossfade(enableCrossFade)
            .error(placeHolderImage)
            .placeholder(placeHolderImage)
            .build(),
        contentScale = contentScale,
        contentDescription = contentDescription,
        modifier = modifier
    )
}