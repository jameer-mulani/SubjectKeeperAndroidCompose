package com.jameermulani.subjectkeepercompose.presentation.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScaffoldWithContent(
    title: String,
    navigationIcon : @Composable () -> Unit,
    scaffoldContent: @Composable () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(text = title)
            },
                navigationIcon = {
                    navigationIcon()
                })
        },
    ) {
        Box(modifier = Modifier.padding(it)) {
            scaffoldContent()
        }
    }
}