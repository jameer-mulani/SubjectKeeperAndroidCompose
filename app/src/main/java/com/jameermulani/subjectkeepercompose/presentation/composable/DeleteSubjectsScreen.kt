package com.jameermulani.subjectkeepercompose.presentation.composable

import android.app.Activity
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.jameermulani.subjectkeepercompose.R
import com.jameermulani.subjectkeepercompose.presentation.state.StateResourceStatus.Failed
import com.jameermulani.subjectkeepercompose.presentation.state.StateResourceStatus.Loading
import com.jameermulani.subjectkeepercompose.presentation.state.StateResourceStatus.Success
import com.jameermulani.subjectkeepercompose.presentation.state.StateResourceStatus.Undefined
import com.jameermulani.subjectkeepercompose.presentation.viewmodel.SubjectViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeleteSubjectScreen(
    subjectViewModel: SubjectViewModel = hiltViewModel()
) {

    val state = subjectViewModel.allSubjectsState.value
    val context = LocalContext.current
    var deleteAllChecked by remember { mutableStateOf(false) }
    val deleteAllCheckedListener = { checked: Boolean ->
        deleteAllChecked = checked
        subjectViewModel.toggleAllSubjectSelection(checked)
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(title = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    if(state.data?.isNotEmpty() == true){
                        Checkbox(
                            checked = deleteAllChecked,
                            onCheckedChange = deleteAllCheckedListener
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                    }
                    Text(text = stringResource(id = R.string.delete_subject))
                }
            },
                actions = {
                    AnimatedVisibility(visible = deleteAllChecked) {
                        Icon(
                            imageVector = Icons.Default.Delete, contentDescription = stringResource(
                                id = R.string.delete_subject
                            ), modifier = Modifier.padding(4.dp).clickable {
                                subjectViewModel.deleteAllCheckedSubjects()
                            }
                        )
                    }
                },
                navigationIcon = {
                    Icon(
                        imageVector = Icons.Default.ArrowBack, contentDescription = stringResource(
                            id = R.string.go_to_previous_screen
                        ), modifier = Modifier.clickable {
                            try {
                                val activity = context as Activity
                                activity.finish()
                            } catch (_: Exception) {

                            }
                        }
                    )
                })
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
        ) {
            when (state.state) {
                Loading -> LoadingUi(modifier = Modifier.align(Alignment.Center))
                Success -> SubjectListUi(
                    list = state.data ?: listOf(), modifier = Modifier.align(
                        Alignment.TopStart
                    )
                )

                Failed -> FailedToLoadUi(
                    errorLine1 = "Could not load list",
                    errorLine2 = "Please try again"
                )

                Undefined -> {
                    subjectViewModel.getAllSubjects()
                }
            }
        }
    }

}