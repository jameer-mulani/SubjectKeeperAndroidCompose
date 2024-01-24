package com.jameermulani.subjectkeepercompose.presentation.composable.model

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.vector.ImageVector

data class DrawerItem(
    @StringRes
    val label: Int,
    val icon: ImageVector,
    val onClick: (DrawerItem) -> Unit
)
