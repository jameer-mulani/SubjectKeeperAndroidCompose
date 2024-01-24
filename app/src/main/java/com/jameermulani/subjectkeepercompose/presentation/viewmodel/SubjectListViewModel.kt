package com.jameermulani.subjectkeepercompose.presentation.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jameermulani.subjectkeepercompose.domain.usecase.GetAllSubjectsUseCase
import com.jameermulani.subjectkeepercompose.presentation.composable.model.SubjectListItemModel
import com.jameermulani.subjectkeepercompose.presentation.state.StateResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SubjectListViewModel @Inject constructor(
    private val getAllSubjectsUseCase: GetAllSubjectsUseCase
) : ViewModel() {

    private val _allSubjectsState = mutableStateOf(
        StateResource<List<SubjectListItemModel>>()
    )

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        _allSubjectsState.value =
            StateResource.failed<List<SubjectListItemModel>>(throwable.message.toString())
    }

    val allSubjectsState: State<StateResource<List<SubjectListItemModel>>> = _allSubjectsState

    private suspend fun getAllSubjectsInternal(): List<SubjectListItemModel> {
        return withContext(Dispatchers.IO + coroutineExceptionHandler) {
            val allSubjectsUseCase = getAllSubjectsUseCase(null)
            allSubjectsUseCase.map {
                SubjectListItemModel(
                    subjectName = it.subjectName,
                    subjectCoverUrl = it.subjectCoverUrl,
                    lastCreatedOn = "Today"
                )
            }
        }
    }

    fun getAllSubjects() {
        viewModelScope.launch {
            val allSubjectsModel = getAllSubjectsInternal()
            _allSubjectsState.value = StateResource.success(data = allSubjectsModel)
        }
    }

}

