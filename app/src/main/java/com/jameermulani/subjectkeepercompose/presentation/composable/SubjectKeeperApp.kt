package com.jameermulani.subjectkeepercompose.presentation.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.jameermulani.subjectkeepercompose.R
import com.jameermulani.subjectkeepercompose.presentation.composable.model.DrawerItem
import kotlinx.coroutines.launch

@Composable
fun SubjectKeeperApp() {

    val navController = rememberNavController()
    val startDestination = Route.SubjectListScreen.name
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
        navController.navigate(Route.SubjectListScreen.name) {
            launchSingleTop = true
        }
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
    val routeState = navController.currentBackStackEntryAsState()


    SubjectAppScaffoldWithDrawer(drawerState = drawerState, drawerItems, selectedDrawerItem) {
        ScaffoldWithContent(
            stringResource(id = scaffoldTitle),
            navigationIcon = {

                when (routeState.value?.destination?.route) {

                    Route.SearchImageScreen.name -> {
                        scaffoldTitle = R.string.search_image
                        NavigationIconForSubScreen {
                            navController.popBackStack()
                        }
                    }

                    Route.CreateSubjectScreen.name -> {
                        scaffoldTitle = R.string.create_subject
                        NavigationIconForSubScreen {
                            navController.popBackStack()
                        }
                    }

                    Route.SubjectListScreen.name -> {
                        scaffoldTitle = R.string.subjects
                        NavigationIconForHome {
                            coroutineScope.launch { drawerState.open() }
                        }
                    }

                    else -> {

                    }
                }

            }) {
            NavHost(navController = navController, startDestination = startDestination) {
                composable(route = startDestination) {
                    SubjectListScreen(
                        navHostController = navController,
                        onAddSubjectClickListener = {
                            navController.navigate(Route.CreateSubjectScreen.name) {
                                launchSingleTop = true
                            }
                        })
                }

                composable(route = Route.CreateSubjectScreen.name) {
                    //get the selected image_url when user selects the image from searchImageScreen
                    val selectedImage = it.savedStateHandle.get<String>("image_url").orEmpty()
                    CreateSubjectScreen(
                        selectedImage = selectedImage,
                        navHostController = navController, onSubjectCoverImageClickListener = {
                        navController.navigate(Route.SearchImageScreen.name)
                    })
                }

                composable(route = Route.SearchImageScreen.name) {
                    ImageSearchScreen(onImageSelectedListener = {imageUrl ->
                        navController.previousBackStackEntry?.savedStateHandle?.set("image_url", imageUrl)
                        navController.popBackStack()
                    })
                }
            }
        }
    }
}

@Composable
fun NavigationIconForSubScreen(onClick : ()->Unit) {
    Icon(
        Icons.Default.ArrowBack,
        contentDescription = "",
        modifier = Modifier
            .padding(12.dp)
            .clickable {
                onClick()
            })
}

@Composable
fun NavigationIconForHome(onClick : ()->Unit) {
    Icon(
        Icons.Default.Menu,
        contentDescription = "",
        modifier = Modifier
            .padding(12.dp)
            .clickable {
                onClick()
            })
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

sealed class Route(val name: String) {
    data object SubjectListScreen : Route("subjects")

    data object CreateSubjectScreen : Route("create_subject")
    data object SearchImageScreen : Route("search_image")
    data object BinScreen : Route("bin")
    data object FavoriteScreen : Route("favorite")
    data object SettingScreen : Route("setting")
}