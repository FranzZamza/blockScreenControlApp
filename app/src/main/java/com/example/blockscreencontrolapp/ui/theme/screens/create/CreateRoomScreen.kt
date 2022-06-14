package com.example.blockscreencontrolapp.ui.theme.screens.create

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import com.example.blockscreencontrolapp.R
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.blockscreencontrolapp.navigation.NavigationTree
import com.example.blockscreencontrolapp.ui.theme.AppTheme
import com.example.blockscreencontrolapp.ui.theme.components.BlockScreenBottomBar
import com.example.blockscreencontrolapp.ui.theme.components.BlockScreenTitle
import com.example.blockscreencontrolapp.ui.theme.screens.create.models.CreateAction
import com.example.blockscreencontrolapp.ui.theme.screens.create.models.CreateRoomEvent
import com.example.blockscreencontrolapp.ui.theme.screens.create.models.CreateViewState
import com.example.blockscreencontrolapp.ui.theme.screens.create.views.CreateRoomView
import com.example.blockscreencontrolapp.ui.theme.screens.create.views.YourRoomView

@Composable
fun CreateRoomScreen(createRoomViewModel: CreateRoomViewModel, navController: NavHostController) {
    val viewState = createRoomViewModel.viewState.observeAsState(CreateViewState())
    //val guests = createRoomViewModel.guests.observeAsState()

    Box(
        Modifier
            .fillMaxSize()
            .background(AppTheme.colors.form)
    ) {
        Column(modifier = Modifier.padding(top = 36.dp, start = 18.dp, end = 18.dp)) {
            when (viewState.value.createAction) {
                CreateAction.None -> BlockScreenTitle(id = R.string.create_screen_title)
                CreateAction.OpenRoom -> BlockScreenTitle(id = R.string.room_title)
            }
            when (viewState.value.createAction) {
                CreateAction.None -> CreateRoomView(viewState = viewState,
                    onChangeRoomName = {
                        createRoomViewModel.obtainEvent(
                            CreateRoomEvent.OnChangeRoomName(
                                it
                            )
                        )
                    },
                    onChangeRoomPassword = {
                        createRoomViewModel.obtainEvent(
                            CreateRoomEvent.OnChangeRoomPassword(
                                it
                            )
                        )
                    },
                    onCreateClick = { createRoomViewModel.obtainEvent(CreateRoomEvent.ClickCreateButton) }
                )
                CreateAction.OpenRoom -> YourRoomView(
                    //guests,
                    viewState = viewState,
                    viewModel = createRoomViewModel
                )
            }
        }
        Row(modifier = Modifier.fillMaxSize(), verticalAlignment = Alignment.Bottom) {
            BlockScreenBottomBar(
                startScreen = NavigationTree.CreateScreen.name,
                navController = navController
            )
        }
    }
}
