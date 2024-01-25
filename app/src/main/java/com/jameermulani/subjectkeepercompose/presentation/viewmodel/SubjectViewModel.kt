package com.jameermulani.subjectkeepercompose.presentation.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jameermulani.subjectkeepercompose.domain.model.Subject
import com.jameermulani.subjectkeepercompose.domain.usecase.AddSubjectUseCase
import com.jameermulani.subjectkeepercompose.domain.usecase.GetAllSubjectsUseCase
import com.jameermulani.subjectkeepercompose.presentation.composable.model.SaveSubjectState
import com.jameermulani.subjectkeepercompose.presentation.composable.model.SubjectListItemModel
import com.jameermulani.subjectkeepercompose.presentation.state.StateResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class SubjectViewModel @Inject constructor(
    private val getAllSubjectsUseCase: GetAllSubjectsUseCase,
    private val addSubjectUseCase: AddSubjectUseCase
) : ViewModel() {

    private val _allSubjectsState = mutableStateOf(
        StateResource<List<SubjectListItemModel>>()
    )
    val allSubjectsState: State<StateResource<List<SubjectListItemModel>>> = _allSubjectsState

    private val _saveSubjectState = mutableStateOf(StateResource<SaveSubjectState>())
    val saveSubjectState: State<StateResource<SaveSubjectState>> = _saveSubjectState

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        _allSubjectsState.value =
            StateResource.failed<List<SubjectListItemModel>>(throwable.message.toString())
    }


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

    fun saveSubject(subjectName: String, subjectCoverUrl: String) {
        viewModelScope.launch {
            val result = saveSubjectInternal(subjectName, subjectCoverUrl)
            if (result.success) {
                _saveSubjectState.value = StateResource.success(result)
            } else {
                _saveSubjectState.value = StateResource.failed<SaveSubjectState>(result.error!!)
            }
        }
    }

    private suspend fun saveSubjectInternal(
        subjectName: String,
        subjectCoverUrl: String
    ): SaveSubjectState {
       return withContext(Dispatchers.IO + coroutineExceptionHandler) {
            if (subjectName.isEmpty()) {
                return@withContext SaveSubjectState(success = false, error = "Empty subject name")
            }
            val subject = Subject(
                subjectName = subjectName,
                subjectCoverUrl = subjectCoverUrl,
                createdDate = Date()
            )
            val result = addSubjectUseCase(subject)
            return@withContext SaveSubjectState(
                success = result,
                error = if (result) null else "Failed to save Subject"
            )
        }
    }

}

