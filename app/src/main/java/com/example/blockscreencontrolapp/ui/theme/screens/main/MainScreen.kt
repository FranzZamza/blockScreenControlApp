package com.example.blockscreencontrolapp.ui.theme.screens.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.blockscreencontrolapp.R
import com.example.blockscreencontrolapp.navigation.NavigationTree
import com.example.blockscreencontrolapp.ui.theme.AppTheme
import com.example.blockscreencontrolapp.ui.theme.components.BlockScreenBottomBar
import com.example.blockscreencontrolapp.ui.theme.components.BlockScreenTitle
import com.example.blockscreencontrolapp.ui.theme.screens.main.models.MainAction
import com.example.blockscreencontrolapp.ui.theme.screens.main.models.MainEvent
import com.example.blockscreencontrolapp.ui.theme.screens.main.models.MainViewState
import com.example.blockscreencontrolapp.ui.theme.screens.main.views.FindRoomView
import com.example.blockscreencontrolapp.ui.theme.screens.main.views.RoomView

@Composable
fun MainScreen(
    mainViewModel: MainViewModel, navController: NavHostController,
) {
    val viewState = mainViewModel.viewState.observeAsState(MainViewState())
    Box(
        modifier = Modifier
            .background(AppTheme.colors.form)
            .fillMaxSize()
    ) {
        Column(
            Modifier
                .background(AppTheme.colors.form)
                .padding(top = 36.dp, start = 18.dp, end = 18.dp)
        ) {
            when (viewState.value.mainAction) {
                MainAction.None -> BlockScreenTitle(id = R.string.main_title)
                MainAction.OpenRoom -> BlockScreenTitle(id = R.string.room_title)
            }
            Spacer(modifier = Modifier.height(24.dp))
            when (viewState.value.mainAction) {
                MainAction.None -> FindRoomView(
                    viewState = viewState,
                    onRoomNameChanged = { mainViewModel.obtainEvent(MainEvent.OnChangeRoomName(it)) },
                    onPasswordChanged = {
                        mainViewModel.obtainEvent(
                            MainEvent.OnChangeRoomPassword(
                                it
                            )
                        )
                    },
                    clickOnFindButton = { mainViewModel.obtainEvent(MainEvent.ClickOnConnect) }
                )
                MainAction.OpenRoom -> RoomView(onChangeStatusScreen = {
                    mainViewModel.obtainEvent(MainEvent.OnChangeScreenStatus)
                }, onRoomScreenDestroy = { mainViewModel.obtainEvent(MainEvent.DestroyRoomScreen) })
            }
            Spacer(modifier = Modifier.height(24.dp))
        }
        Row(modifier = Modifier.fillMaxSize(), verticalAlignment = Alignment.Bottom) {
            BlockScreenBottomBar(
                startScreen = NavigationTree.MainScreen.name,
                navController = navController
            )
        }
    }

}




