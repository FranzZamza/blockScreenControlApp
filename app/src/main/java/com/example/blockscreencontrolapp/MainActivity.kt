package com.example.blockscreencontrolapp

import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import com.example.blockscreencontrolapp.ui.theme.BlockScreenControlAppTheme
import com.example.blockscreencontrolapp.ui.theme.common.Receiver
import com.example.blockscreencontrolapp.ui.theme.screens.ApplicationScreen
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        IntentFilter(Intent.ACTION_SCREEN_OFF).also {
            registerReceiver(Receiver(), it)
        }
        IntentFilter(Intent.ACTION_SCREEN_ON).also {
            registerReceiver(Receiver(), it)
        }
        setContent {
            BlockScreenControlAppTheme {
                val systemUiController = rememberSystemUiController()
                SideEffect {
                    systemUiController.setSystemBarsColor(
                        color = Color.Transparent,
                        darkIcons = true
                    )
                }
                ApplicationScreen()
            }

        }
    }

    override fun onStop() {
        super.onStop()
        Receiver().onDestroyApp()
    }

}