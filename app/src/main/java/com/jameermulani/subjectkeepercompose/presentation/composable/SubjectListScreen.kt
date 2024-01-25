package com.jameermulani.subjectkeepercompose.presentation.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.jameermulani.subjectkeepercompose.presentation.composable.model.SubjectListItemModel
import com.jameermulani.subjectkeepercompose.presentation.state.StateResourceStatus.Failed
import com.jameermulani.subjectkeepercompose.presentation.state.StateResourceStatus.Loading
import com.jameermulani.subjectkeepercompose.presentation.state.StateResourceStatus.Success
import com.jameermulani.subjectkeepercompose.presentation.state.StateResourceStatus.Undefined
import com.jameermulani.subjectkeepercompose.presentation.viewmodel.SubjectViewModel

@Composable
fun SubjectListScreen(
    navHostController: NavHostController,
    onAddSubjectClickListener: () -> Unit,
    subjectListViewModel: SubjectViewModel = hiltViewModel()
) {

    val state = subjectListViewModel.allSubjectsState
    LaunchedEffect(key1 = "get_subjects") {
        subjectListViewModel.getAllSubjects()
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        contentAlignment = Alignment.Center
    ) {
        when (state.value.state) {
            Loading -> LoadingUi()
            Success -> SubjectListUi(state.value.data ?: listOf())
            Failed -> FailedToLoadUi(
                errorLine1 = "Could not load list",
                errorLine2 = "Please try again"
            )

            Undefined -> subjectListViewModel.getAllSubjects()
        }

        FloatingActionButton(
            onClick = onAddSubjectClickListener,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        ) {
            Icon(imageVector = Icons.Default.Add, contentDescription = "Add subject")
        }
    }


}

@Composable
fun SubjectListUi(list: List<SubjectListItemModel>) {

    if (list.isEmpty()) {
        FailedToLoadUi(
            errorLine1 = "No Subjects to Show",
            errorLine2 = "To add Subject click on + button"
        )
    } else {
        LazyColumn() {
            items(list) {
                SubjectListItem(
                    modifier = Modifier.padding(2.dp),
                    subjectListItemModel = it
                )
            }
        }
    }
}

@Composable
fun SubjectListItem(
    subjectListItemModel: SubjectListItemModel,
    modifier: Modifier = Modifier
) {

    Card(
        modifier = modifier
            .fillMaxWidth()
            .then(modifier),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
        ) {
            AsyncImageComposable(
                url = subjectListItemModel.subjectCoverUrl,
                modifier = Modifier
                    .size(120.dp)
            )
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = subjectListItemModel.subjectName,
                    style = MaterialTheme.typography.titleLarge
                )
            }
        }
    }
}

@Composable
fun FailedToLoadUi(errorLine1: String, errorLine2: String, modifier: Modifier = Modifier) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(text = errorLine1, style = MaterialTheme.typography.titleLarge)
        Text(text = errorLine2, style = MaterialTheme.typography.bodyLarge)
    }
}

@Composable
fun LoadingUi() {
    CircularProgressIndicator()
}