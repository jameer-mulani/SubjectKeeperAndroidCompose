package com.jameermulani.subjectkeepercompose.usecases.implementation

import com.jameermulani.subjectkeepercompose.data.model.SearchedImageResponse
import com.jameermulani.subjectkeepercompose.data.respository.NetworkRepositoryImpl
import com.jameermulani.subjectkeepercompose.domain.usecase.SuspendUseCase
import javax.inject.Inject

class SearchImageUseCase @Inject constructor(
    private val networkRepo: NetworkRepositoryImpl
) : SuspendUseCase<String, SearchedImageResponse> {
    override suspend fun invoke(input: String): SearchedImageResponse {
        return networkRepo.searchImage(input)
    }
}