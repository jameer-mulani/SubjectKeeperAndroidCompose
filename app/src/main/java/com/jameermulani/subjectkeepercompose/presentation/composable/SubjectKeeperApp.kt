package com.jameermulani.subjectkeepercompose.presentation.composable

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.jameermulani.subjectkeepercompose.R
import com.jameermulani.subjectkeepercompose.presentation.composable.model.DrawerItem
import kotlinx.coroutines.launch

@Composable
fun SubjectKeeperApp() {

    val navController = rememberNavController()
    val startDestination = Routes.SubjectListScreen
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()
    var scaffoldTitle by remember { mutableStateOf(R.string.subjects) }

    val profileDrawerItemOnClickListener: (DrawerItem) -> Unit = { di: DrawerItem ->
        scaffoldTitle = di.label
        coroutineScope.launch { drawerState.close() }
    }

    val binDrawerItemOnClickListener: (DrawerItem) -> Unit = { di: DrawerItem ->
        scaffoldTitle = di.label
        coroutineScope.launch { drawerState.close() }
    }

    val subjectDrawerItemOnClickListener: (DrawerItem) -> Unit = { di: DrawerItem ->
        scaffoldTitle = di.label
        navController.navigate(Routes.SubjectListScreen)
        coroutineScope.launch { drawerState.close() }
    }

    val subjectsDrawerItem = DrawerItem(
        label = R.string.subjects,
        icon = Icons.Default.Person,
        onClick = subjectDrawerItemOnClickListener
    )

    val profileDrawerItem = DrawerItem(
        label = R.string.profile,
        icon = Icons.Default.Person,
        onClick = profileDrawerItemOnClickListener
    )
    val binDrawerItem = DrawerItem(
        label = R.string.bin,
        icon = Icons.Default.Delete,
        onClick = binDrawerItemOnClickListener
    )


    val drawerItems = listOf(
        subjectsDrawerItem,
        profileDrawerItem,
        binDrawerItem
    )
    var selectedDrawerItem by remember { mutableStateOf(drawerItems.first()) }

    SubjectAppScaffoldWithDrawer(drawerState = drawerState, drawerItems, selectedDrawerItem) {
        ScaffoldWithContent(
            stringResource(id = scaffoldTitle),
            menuClickListener = { coroutineScope.launch { drawerState.open() } }) {
            NavHost(navController = navController, startDestination = startDestination) {
                composable(route = startDestination) {
                    SubjectListScreen()
                }
            }
        }
    }

}


@Composable
fun SubjectAppScaffoldWithDrawer(
    drawerState: DrawerState,
    drawerItems: List<DrawerItem>,
    selectedDrawerItem: DrawerItem,
    content: @Composable () -> Unit
) {

    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = true,
        drawerContent = {
            DrawerContent(drawerItems, selectedDrawerItem)
        }) {
        content()
    }
}

object Routes {
    const val SubjectListScreen = "subjects"
}