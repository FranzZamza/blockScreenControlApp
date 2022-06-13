package com.example.blockscreencontrolapp.ui.theme.common

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await


class Receiver : BroadcastReceiver() {

    @OptIn(DelicateCoroutinesApi::class)
    fun onDestroyApp() {
        if (ScreenListener.roomKey.isNotEmpty() && ScreenListener.uid.isNotEmpty()) {
            GlobalScope.launch(Dispatchers.IO) {
                val uid = Firebase.auth.uid
                Firebase.database.reference.child("rooms").child(ScreenListener.roomKey)
                    .child("users")
                    .child("$uid")
                    .onDisconnect()
                    .removeValue()
                    .await()
                Log.e("Close", "yes")
            }
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    override fun onReceive(context: Context?, intent: Intent?) {
        if (ScreenListener.uid.isNotEmpty() && ScreenListener.roomKey.isNotEmpty())
            when (intent!!.action) {
                Intent.ACTION_SCREEN_OFF -> {
                    GlobalScope.launch(Dispatchers.IO) {
                        Firebase.database.reference.child("rooms").child(ScreenListener.roomKey)
                            .child("users").child(ScreenListener.uid)
                            .child("isScreenOff").setValue("true").await()
                    }
                }
                Intent.ACTION_SCREEN_ON -> {
                    GlobalScope.launch(Dispatchers.IO) {
                        Firebase.database.reference.child("rooms").child(ScreenListener.roomKey)
                            .child("users").child(ScreenListener.uid)
                            .child("isScreenOff").setValue("false").await()
                    }
                }
            }
    }

    class ScreenListener() {
        companion object {
            var roomKey = ""
            var uid = ""
        }
    }
}
/*
      val uid = Firebase.auth.uid
                    Firebase.database.reference.child("rooms").child(ScreenListener.roomKey)
                        .child("users")
                        .child("$uid")
                        .removeValue()
 */