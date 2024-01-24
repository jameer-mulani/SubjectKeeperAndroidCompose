package com.jameermulani.subjectkeepercompose.presentation.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.jameermulani.subjectkeepercompose.presentation.composable.model.DrawerItem

@Composable
fun DrawerContent(items: List<DrawerItem>, selected: DrawerItem) {
    ModalDrawerSheet(
        modifier = Modifier.width(250.dp),
    ) {
        Spacer(modifier = Modifier.height(20.dp))
        items.forEach {
            NavigationDrawerItem(label = {
                DrawerMenuItem(drawerItem = it)
            }, selected = it == selected, onClick = {
                it.onClick(it)
            })
        }
    }
}

@Composable
fun DrawerMenuItem(drawerItem: DrawerItem) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start,
        modifier = Modifier.fillMaxWidth()
    ) {
        Icon(imageVector = drawerItem.icon, contentDescription = "")
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = stringResource(id = drawerItem.label), style = MaterialTheme.typography.labelLarge)
    }
}