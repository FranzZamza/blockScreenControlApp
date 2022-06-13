package com.example.blockscreencontrolapp.ui.theme.screens

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.blockscreencontrolapp.navigation.NavigationTree
import com.example.blockscreencontrolapp.ui.theme.screens.create.CreateRoomScreen
import com.example.blockscreencontrolapp.ui.theme.screens.create.CreateRoomViewModel
import com.example.blockscreencontrolapp.ui.theme.screens.login.LoginScreen
import com.example.blockscreencontrolapp.ui.theme.screens.login.LoginViewModel
import com.example.blockscreencontrolapp.ui.theme.screens.main.MainScreen
import com.example.blockscreencontrolapp.ui.theme.screens.main.MainViewModel
import com.example.blockscreencontrolapp.ui.theme.screens.splash.SplashScreen

@Composable
fun ApplicationScreen() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = NavigationTree.Splash.name) {
        composable(NavigationTree.Splash.name) { SplashScreen(navController) }
        composable(NavigationTree.Login.name) {
            val loginViewModel = hiltViewModel<LoginViewModel>()
            LoginScreen(loginViewModel, navController)
        }
        composable(NavigationTree.MainScreen.name) {
            val mainViewModel = hiltViewModel<MainViewModel>()
            MainScreen(mainViewModel, navController)
        }
        composable(NavigationTree.CreateScreen.name) {
            val createRoomViewModel = hiltViewModel<CreateRoomViewModel>()
            CreateRoomScreen(createRoomViewModel, navController)
        }
    }
}