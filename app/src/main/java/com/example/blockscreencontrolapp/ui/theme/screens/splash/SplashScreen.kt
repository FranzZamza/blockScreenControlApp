package com.example.blockscreencontrolapp.ui.theme.screens.splash

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavController
import com.example.blockscreencontrolapp.navigation.NavigationTree
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import javax.annotation.meta.When


@Composable
fun SplashScreen(navController: NavController) {
    LaunchedEffect(key1 = Unit, block = {
        if (Firebase.auth.currentUser != null)
            navController.navigate(NavigationTree.MainScreen.name)
        else navController.navigate(NavigationTree.Login.name)
    })
}