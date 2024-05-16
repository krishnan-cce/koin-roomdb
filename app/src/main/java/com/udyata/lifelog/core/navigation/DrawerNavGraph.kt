package com.udyata.lifelog.core.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.udyata.lifelog.DairyEntries
import com.udyata.lifelog.MainContent
import com.udyata.lifelog.domain.state.CustomDrawerState
import com.udyata.lifelog.domain.state.opposite
import com.udyata.lifelog.presentation.DiaryViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DrawerNavGraph(
    modifier: Modifier,
    navHostController: NavHostController,
    drawerState: CustomDrawerState,
    onDrawerClick: (CustomDrawerState) -> Unit
) {
    Surface(modifier = modifier.fillMaxSize()) {
        NavHost(
            navController = navHostController,
            startDestination = NavigationItem.Home.route,
        ) {
            composable(NavigationItem.Home.route) {
                MainContent(
                    drawerState = drawerState,
                    onDrawerClick = onDrawerClick,
                    route = "Home"
                )
            }
            composable(NavigationItem.Profile.route) {
                val viewModel: DiaryViewModel= koinViewModel()
                Scaffold (
                    topBar = {
                        TopAppBar(
                            title = { Text(text = "Dairy") },
                            navigationIcon = {
                                IconButton(onClick = { onDrawerClick(drawerState.opposite()) }) {
                                    Icon(
                                        imageVector = Icons.Default.Menu,
                                        contentDescription = "Dairy Icon"
                                    )
                                }
                            }
                        )
                    }
                ){
                    Column(modifier = Modifier.fillMaxSize().padding(it)) {
                        DairyEntries(viewModel)
                    }
                }
            }
            composable(NavigationItem.Premium.route) {
                MainContent(
                    drawerState = drawerState,
                    onDrawerClick = onDrawerClick,
                    route = "Premium"
                )
            }
            composable(NavigationItem.Settings.route) {
                MainContent(
                    drawerState = drawerState,
                    onDrawerClick = onDrawerClick,
                    route = "Settings"
                )
            }
        }
    }
}