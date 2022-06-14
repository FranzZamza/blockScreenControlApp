package com.example.blockscreencontrolapp.ui.theme.screens.create

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.blockscreencontrolapp.ui.theme.common.EventHandler
import com.example.blockscreencontrolapp.ui.theme.common.Guest
import com.example.blockscreencontrolapp.ui.theme.screens.create.models.CreateAction
import com.example.blockscreencontrolapp.ui.theme.screens.create.models.CreateRoomEvent
import com.example.blockscreencontrolapp.ui.theme.screens.create.models.CreateViewState
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
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

    private val _guests = MutableLiveData<List<Guest>>()
    val guests: LiveData<List<Guest>> = _guests

    private val _roomKey = MutableLiveData("")

    override fun obtainEvent(event: CreateRoomEvent) {
        when (event) {
            is CreateRoomEvent.OnChangeRoomName -> roomNameChanged(event.value)
            is CreateRoomEvent.OnChangeRoomPassword -> roomPasswordChanged(event.value)
            CreateRoomEvent.ClickCreateButton -> createRoom(
                roomName = _viewState.value!!.roomName,
                roomPassword = _viewState.value!!.roomPassword
            )
            // CreateRoomEvent.OnChangeData->
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


    fun listenDataChange() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val data = Firebase.database.reference.child("rooms").child(_roomKey.value!!)
                    .child("users")
                while (_viewState.value?.createAction == CreateAction.OpenRoom) {
                    data.addValueEventListener(object : ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            getGuestFromRoom(snapshot)
                        }

                        override fun onCancelled(error: DatabaseError) {

                        }
                    })
                }
            } catch (e: Exception) {
            }
        }
    }


  private  fun getGuestFromRoom(snapshot: DataSnapshot) {
        val guests = mutableListOf<Guest>()
        snapshot.children.forEach {
            val name = it.child("name").value.toString()
            val isScreenOff = it.child("isScreenOff").value.toString()
            guests.add(Guest(name, isScreenOff))
        }
        _viewState.postValue(_viewState.value?.copy(guests = guests))
    }

/*
 val data = Firebase.database.reference.child("rooms").child(_roomKey.value!!)
                    .child("users")
   val guests = mutableListOf<Guest>()
   for (guest in snapshot.children) {
                            val name = guest.child("name").value.toString()
                            val isScreenOff = guest.child("isScreenOff").value.toString()
                            guests.add(Guest(name, isScreenOff))
                            Log.e("_guests", _guests.value.toString())
                        }
                        _viewState.postValue(_viewState.value?.copy(guests = guests))
                        _guests.postValue(guests)
                        Log.e("_guests", _guests.value.toString())
 */

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