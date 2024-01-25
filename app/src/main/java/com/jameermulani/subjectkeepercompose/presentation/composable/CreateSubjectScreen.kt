package com.jameermulani.subjectkeepercompose.presentation.composable

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.jameermulani.subjectkeepercompose.R
import com.jameermulani.subjectkeepercompose.presentation.state.StateResourceStatus.Failed
import com.jameermulani.subjectkeepercompose.presentation.state.StateResourceStatus.Success
import com.jameermulani.subjectkeepercompose.presentation.viewmodel.SubjectViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateSubjectScreen(
    selectedImage : String,
    navHostController: NavHostController,
    onSubjectCoverImageClickListener : ()->Unit,
    subjectViewModel: SubjectViewModel = hiltViewModel()
) {

    var subjectNameText by remember { mutableStateOf("") }
    val subjectNameTextChangeListener: (String) -> Unit = { value: String ->
        subjectNameText = value
    }

    val saveSubjectState = subjectViewModel.saveSubjectState.value

    LaunchedEffect(key1 = saveSubjectState.state) {
        when (saveSubjectState.state) {
            Success -> {
                navHostController.popBackStack()
            }

            else -> {
                // no-ops
            }
        }
    }

    if (saveSubjectState.state == Failed) {
        Toast.makeText(LocalContext.current, saveSubjectState.error, Toast.LENGTH_SHORT).show()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp),
    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            AsyncImageComposable(url = selectedImage, modifier = Modifier
                .size(92.dp)
                .clickable {
                    onSubjectCoverImageClickListener()
                })
            Spacer(modifier = Modifier.height(12.dp))
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = subjectNameText,
                onValueChange = subjectNameTextChangeListener,
                placeholder = {
                    Text(text = stringResource(R.string.enter_subject_name))
                },
                label = {
                    Text(text = stringResource(R.string.subject_name))
                },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                singleLine = true,

            )
            Spacer(modifier = Modifier.height(12.dp))
            Button(onClick = {
                subjectViewModel.saveSubject(
                    subjectName = subjectNameText,
                    subjectCoverUrl = selectedImage
                )
            }) {
                Text(text = stringResource(R.string.save_subject))
            }
        }
    }
}