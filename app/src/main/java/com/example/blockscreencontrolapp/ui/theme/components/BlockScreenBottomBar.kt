package com.example.blockscreencontrolapp.ui.theme.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomAppBar
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.blockscreencontrolapp.R
import com.example.blockscreencontrolapp.navigation.NavigationTree
import com.example.blockscreencontrolapp.ui.theme.AppTheme

@Composable
fun BlockScreenBottomBar(
    startScreen:String,
    navController: NavController
) {
    val bottomAppBarContext = remember {
        mutableStateOf(startScreen)
    }


    BottomAppBar(modifier = Modifier.fillMaxWidth(), backgroundColor = AppTheme.colors.outline) {
        Row(horizontalArrangement = Arrangement.SpaceEvenly) {
            IconButton(onClick = {
                bottomAppBarContext.value = NavigationTree.MainScreen.name
                navController.navigate(bottomAppBarContext.value)
            }, modifier = Modifier.weight(1f)) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_meeting_room),
                    contentDescription = null,
                    modifier = Modifier.size(30.dp),
                    tint = if (bottomAppBarContext.value == NavigationTree.MainScreen.name) {
                        AppTheme.colors.secondary
                    } else {
                        AppTheme.colors.primary
                    }
                )
            }

            IconButton(
                onClick = {
                    bottomAppBarContext.value = NavigationTree.CreateScreen.name
                    navController.navigate(bottomAppBarContext.value)
                },
                modifier = Modifier.weight(1f)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_baseline_add_circle_outline_24),
                    contentDescription = null,
                    modifier = Modifier.size(30.dp),

                    tint = if (bottomAppBarContext.value == NavigationTree.CreateScreen.name) {
                        AppTheme.colors.secondary
                    } else {
                        AppTheme.colors.primary
                    }


                )
            }

            IconButton(
                onClick = {
                    bottomAppBarContext.value = NavigationTree.GridRoomsScreen.name
                    navController.navigate(bottomAppBarContext.value)
                },
                modifier = Modifier.weight(1f)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_grid_view),
                    contentDescription = null,
                    modifier = Modifier.size(30.dp),
                    tint = if (bottomAppBarContext.value == NavigationTree.GridRoomsScreen.name) {
                        AppTheme.colors.secondary
                    } else {
                        AppTheme.colors.primary
                    }
                )
            }
        }
    }
}