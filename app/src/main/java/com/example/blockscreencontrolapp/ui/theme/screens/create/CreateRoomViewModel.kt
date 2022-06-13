package com.example.blockscreencontrolapp.ui.theme.screens.create

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.blockscreencontrolapp.ui.theme.common.EventHandler
import com.example.blockscreencontrolapp.ui.theme.common.Guest
import com.example.blockscreencontrolapp.ui.theme.screens.create.models.CreateAction
import com.example.blockscreencontrolapp.ui.theme.screens.create.models.CreateRoomEvent
import com.example.blockscreencontrolapp.ui.theme.screens.create.models.CreateViewState
import com.example.blockscreencontrolapp.ui.theme.screens.main.models.MainAction
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class CreateRoomViewModel @Inject constructor() : ViewModel(), EventHandler<CreateRoomEvent> {
    private val _viewState = MutableLiveData(CreateViewState())
    val viewState: LiveData<CreateViewState> = _viewState

    private val _roomKey = MutableLiveData("")

    private val _listOfGuests = MutableLiveData<List<Guest>>()
    val listOfGuests: LiveData<List<Guest>> = _listOfGuests

    override fun obtainEvent(event: CreateRoomEvent) {
        when (event) {
            is CreateRoomEvent.OnChangeRoomName -> roomNameChanged(event.value)
            is CreateRoomEvent.OnChangeRoomPassword -> roomPasswordChanged(event.value)
            CreateRoomEvent.ClickCreateButton -> createRoom(
                roomName = _viewState.value!!.roomName,
                roomPassword = _viewState.value!!.roomPassword
            )
        }
    }

    private fun createRoom(roomName: String, roomPassword: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val uid = Firebase.auth.uid
            val roomKey = FirebaseDatabase.getInstance().reference.push().get().await().key
            if (roomKey != null && !isExist(roomName)) {
                _roomKey.postValue(roomKey)
                val firebase = Firebase.database.reference.child("rooms")
                firebase.child(roomKey).child("name").setValue(roomName).await()
                firebase.child(roomKey).child("password").setValue(roomPassword).await()
                firebase.child(roomKey).child("owner").setValue(uid).await()
            }
            openRoom()
        }
    }

    private suspend fun getUserFromRoom() {
        viewModelScope.launch(Dispatchers.IO) {
            val guests = mutableListOf<Guest>()
            try {
                val data = Firebase.database.reference.child("rooms").child(_roomKey.value!!)
                    .child("users").get().await()
                for (guest in data.children) {
                    val name = guest.child("name").value.toString()
                    val isScreenOff = guest.child("isScreenOff").value.toString()
                    guests.add(Guest(name, isScreenOff))
                }
                _listOfGuests.postValue(guests)
            } catch (e: Exception) {
            }
        }
    }

    private fun openRoom() {
        _viewState.postValue(_viewState.value?.copy(createAction = CreateAction.OpenRoom))
    }

    private suspend fun isExist(roomName: String): Boolean {
        val database = FirebaseDatabase.getInstance().reference.child("rooms").get()
            .await().children

        for (item in database) {
            if (item.child("name").value == roomName) return true
        }
        return false
    }

    private fun roomNameChanged(value: String) {
        _viewState.postValue(_viewState.value?.copy(roomName = value))
    }

    private fun roomPasswordChanged(value: String) {
        _viewState.postValue(_viewState.value?.copy(roomPassword = value))
    }
}