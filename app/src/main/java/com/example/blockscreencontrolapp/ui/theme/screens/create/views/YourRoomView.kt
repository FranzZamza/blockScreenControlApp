package com.example.blockscreencontrolapp.ui.theme.screens.create.views

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.blockscreencontrolapp.ui.theme.common.Guest
import com.example.blockscreencontrolapp.ui.theme.screens.create.CreateRoomViewModel
import com.example.blockscreencontrolapp.ui.theme.screens.create.models.CreateViewState

@Composable
fun YourRoomView(
    viewState: State<CreateViewState>,
    viewModel: CreateRoomViewModel
) {

    val list = viewModel.guests.observeAsState()
    viewModel.listenDataChange()
    Text(text = "${viewState.value.guests}")
    //list.value?.let { GuestsList(guests = it) }
    GuestsList(guests = viewState.value.guests)
}

@Composable
fun GuestsList(guests: List<Guest>) {
    Box(modifier = Modifier.padding(top = 18.dp))
    LazyColumn {
        items(guests) { guest ->
            GuestItem(guest = guest)
        }
    }
}

@Composable
fun GuestItem(guest: Guest) {
    Card(
        elevation = 4.dp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 18.dp, start = 16.dp, end = 16.dp)
    ) {
        Column(Modifier.fillMaxSize()) {
            Row(Modifier.fillMaxWidth()) {
                Text(text = "имя ${guest.name}")
            }
            Row(Modifier.fillMaxWidth()) {
                Text(text = "экран ${guest.isScreenOff}")
            }
        }
    }
}
/*
@Composable
fun RequestList(requests: List<Request>, viewModel: MainViewModel) {
    Box(modifier = Modifier.padding(top = 18.dp))
    LazyColumn {
        items(requests) { request ->
            RequestItem(request = request, viewModel)
        }
    }
}
@Composable
fun RequestItem(request: Request, viewModel: MainViewModel) {
    var isConvert by remember {
        mutableStateOf(true)
    }
    GlobalScope.launch(Dispatchers.IO) {
        viewModel.getImageUrlFromStorage(request.image)
    }
    Card(
        elevation = 0.dp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 18.dp, start = 16.dp, end = 16.dp)
            .clickable {
                isConvert = !isConvert
            },
        backgroundColor = Color(0x66C5E4FF),
        border = BorderStroke(color = Color.Transparent, width = 0.dp)

    ) {
        Column(Modifier.padding(horizontal = 16.dp)) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 16.dp),
            ) {
                Text(
                    text = "Topic: ${request.topic}", fontSize = 18.sp,
                    color = Color.DarkGray
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Text(
                    text = "Description: ${request.description}",
                    color = Color(0xFF828583),
                    fontSize = 12.sp,
                    maxLines = if (isConvert) 1 else 20,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(vertical = 16.dp)
            ) {
                Text(
                    text = "Status: Ready",
                    color = Color(0xFF828583),
                    fontSize = 12.sp,
                    maxLines = if (isConvert) 1 else 20,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Row(horizontalArrangement = Arrangement.Center) {
                if (!isConvert) GlideImage(
                    imageModel = request.image,
                    modifier = Modifier.fillMaxSize(),
                    alignment = Alignment.Center
                )
            }
            Row(
                Modifier.fillMaxSize(),
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(id = if (isConvert) R.drawable.ic_arrow_down else R.drawable.ic_arrow_up),
                    contentDescription = null,
                    modifier = Modifier
                        .clickable {
                            isConvert = !isConvert
                        }, alignment = Alignment.BottomCenter
                )
            }
        }

    }
}
 */