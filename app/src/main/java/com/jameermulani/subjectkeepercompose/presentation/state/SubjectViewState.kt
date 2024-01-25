package com.jameermulani.subjectkeepercompose.presentation.state

import com.jameermulani.subjectkeepercompose.presentation.composable.model.SubjectListItemModel

data class SubjectViewState(
    val subjectListItemModels: List<SubjectListItemModel>,
)

data class StateResource<out T>(
    val data: T? = null,
    val error: String? = null,
    val state: StateResourceStatus = StateResourceStatus.Undefined
) {
    companion object {
        fun <T> success(data: T) = StateResource(data = data, state = StateResourceStatus.Success, error = null)
        fun <T> failed(error: String) =
            StateResource(data = null, error = error, state = StateResourceStatus.Failed)

        fun <T> loading() =
            StateResource(data = null, error = null, state = StateResourceStatus.Loading)
    }
}

enum class StateResourceStatus {
    Loading, Success, Failed, Undefined
}
