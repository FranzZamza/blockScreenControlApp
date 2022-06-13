package com.example.blockscreencontrolapp.ui.theme.screens.main
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.blockscreencontrolapp.ui.theme.common.EventHandler
import com.example.blockscreencontrolapp.ui.theme.common.Receiver
import com.example.blockscreencontrolapp.ui.theme.screens.main.models.MainAction
import com.example.blockscreencontrolapp.ui.theme.screens.main.models.MainEvent
import com.example.blockscreencontrolapp.ui.theme.screens.main.models.MainViewState
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel(), EventHandler<MainEvent> {
    private val _viewState = MutableLiveData(MainViewState())
    val viewState: LiveData<MainViewState> = _viewState

    private val _roomKey = MutableLiveData("")

    override fun obtainEvent(event: MainEvent) {
        when (event) {
            is MainEvent.OnChangeScreenStatus -> changeScreenStatus(
                roomKey = _roomKey.value!!
            )
            is MainEvent.OnChangeRoomName -> roomNameChanged(event.value)
            is MainEvent.OnChangeRoomPassword -> roomPasswordChanged(event.value)
            MainEvent.ClickOnConnect -> connectToRoom(
                _viewState.value!!.roomName,
                _viewState.value!!.roomPassword
            )
            MainEvent.DestroyRoomScreen -> deleteUserFromRoom()
        }
    }
    private fun deleteUserFromRoom() {
        viewModelScope.launch(Dispatchers.IO) {
            val uid = Firebase.auth.uid
            Firebase.database.reference.child("rooms").child("${_roomKey.value}").child("users")
                .child("$uid").removeValue()
        }
    }

    private fun changeScreenStatus(roomKey: String) {
        Receiver.ScreenListener.roomKey = roomKey
        Receiver.ScreenListener.uid = Firebase.auth.uid.toString()
    }

    private fun openRoom() {
        _viewState.postValue(_viewState.value?.copy(mainAction = MainAction.OpenRoom))
    }

    private fun roomPasswordChanged(value: String) {
        _viewState.postValue(_viewState.value?.copy(roomPassword = value))
    }

    private fun connectToRoom(roomName: String, roomPassword: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val uid = Firebase.auth.uid
            val userName =
                Firebase.database.reference.child("users").child("$uid").child("name")
                    .get()
                    .await().value
            Log.e("name", userName.toString())
            val rooms = Firebase.database.reference.child("rooms").get().await().children
            for (item in rooms) {
                if (item.child("name").value == roomName
                    && item.child("password").value == roomPassword
                ) {
                    _roomKey.postValue(item.key)
                    Firebase.database.reference.child("rooms").child("${item.key}").child("users")
                        .child("$uid").child("name").setValue(userName.toString())
                    openRoom()
                }
            }
        }
    }

    private fun roomNameChanged(value: String) {
        _viewState.postValue(_viewState.value?.copy(roomName = value))
    }
}
