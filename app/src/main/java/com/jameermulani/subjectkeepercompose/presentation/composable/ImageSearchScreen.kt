package com.jameermulani.subjectkeepercompose.presentation.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.jameermulani.subjectkeepercompose.R
import com.jameermulani.subjectkeepercompose.data.model.SearchedImage
import com.jameermulani.subjectkeepercompose.presentation.state.StateResourceStatus.*
import com.jameermulani.subjectkeepercompose.presentation.viewmodel.ImageViewModel

@Composable
fun ImageSearchScreen(
    onImageSelectedListener: (String) -> Unit,
    imageViewModel: ImageViewModel = hiltViewModel()
) {
    val state = imageViewModel.searchImage.value

    var searchText by remember { mutableStateOf("") }
    val searchTextChangeListener = { it: String ->
        searchText = it
        imageViewModel.searchImage(it)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp),
    ) {

        when (state.state) {
            Loading -> LoadingUi(modifier = Modifier.align(Alignment.Center))
            Success -> ShowImageGridWithSearchBox(
                searchText = searchText,
                searchTextChangeListener = searchTextChangeListener,
                list = state.data ?: listOf(),
                onImageClickListener = onImageSelectedListener
            )

            Failed -> FailedToLoadUi(
                errorLine1 = state.error ?: "Error",
                errorLine2 = "Please try again"
            )

            Undefined -> ShowImageGridWithSearchBox(
                searchText = searchText,
                searchTextChangeListener = searchTextChangeListener,
                list = listOf(),
                onImageClickListener = onImageSelectedListener
            )
        }
    }
}

@Composable
fun ShowImageGridWithSearchBox(
    searchText: String,
    searchTextChangeListener: (String) -> Unit,
    list: List<SearchedImage>, onImageClickListener: (String) -> Unit
) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = searchText,
            onValueChange = searchTextChangeListener,
            placeholder = {
                Text(text = stringResource(R.string.what_images_would_like_to_see))
            },
            label = { Text(text = stringResource(id = R.string.search_image)) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done)
        )
        Spacer(modifier = Modifier.height(4.dp))

        LazyVerticalStaggeredGrid(
            columns = StaggeredGridCells.Fixed(2),
            verticalItemSpacing = 4.dp,
            horizontalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            items(list) {
                GridItem(it, onImageClickListener)
            }
        }

    }
}

@Composable
fun GridItem(image: SearchedImage, onImageClickListener: (String) -> Unit) {
    AsyncImageComposable(
        url = image.previewUrl,
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .clickable {
                onImageClickListener(image.previewUrl)
            })
}
