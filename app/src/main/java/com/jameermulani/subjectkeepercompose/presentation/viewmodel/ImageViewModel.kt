package com.jameermulani.subjectkeepercompose.presentation.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jameermulani.subjectkeepercompose.data.model.SearchedImage
import com.jameermulani.subjectkeepercompose.presentation.state.StateResource
import com.jameermulani.subjectkeepercompose.usecases.implementation.SearchImageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ImageViewModel @Inject constructor(
    private val searchImageUseCase: SearchImageUseCase
) : ViewModel() {

    companion object {
        private const val TAG = "ImageViewModel"
    }

    private val coroutineExceptionHandler =
        CoroutineExceptionHandler { _, throwable -> throwable.printStackTrace() }

    private val _searchImageState =
        mutableStateOf<StateResource<List<SearchedImage>>>(StateResource())
    val searchImage: State<StateResource<List<SearchedImage>>> = _searchImageState

    private var searchingImageJob : Job? = null
    fun searchImage(query: String) {
        searchingImageJob?.cancel()
        searchingImageJob = viewModelScope.launch {
            delay(1000)
            _searchImageState.value = StateResource.loading<List<SearchedImage>>()
            val stateResource = searchImageInternal(query)
            _searchImageState.value = stateResource
        }
    }

    private suspend fun searchImageInternal(query: String): StateResource<List<SearchedImage>> {
        return withContext(Dispatchers.IO + coroutineExceptionHandler) {
            val imageResponse = searchImageUseCase(query)
            return@withContext if (imageResponse.success) {
                StateResource.success(imageResponse.results)
            } else {
                StateResource.failed<List<SearchedImage>>(imageResponse.error ?: "Error")
            }
        }
    }


    override fun onCleared() {
        super.onCleared()
    }

}