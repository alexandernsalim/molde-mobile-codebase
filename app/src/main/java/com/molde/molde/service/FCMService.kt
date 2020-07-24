package com.molde.molde.service

import android.content.Intent
import android.util.Log
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class FCMService : FirebaseMessagingService() {

    companion object {
        const val DISCUSSION = "ACTION_DISCUSSION"
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        Log.i("FCM", remoteMessage.from)
        Log.i("FCM", remoteMessage.data.toString())

        val intent = Intent(DISCUSSION)
        intent.putExtra("discussionId", remoteMessage.data["discussionId"]?.toInt())
        intent.putExtra("shopUsername", remoteMessage.data["shopUsername"])
        intent.putExtra("shopUserUsername", remoteMessage.data["shopUserUsername"])
        intent.putExtra("message", remoteMessage.data["message"])
        LocalBroadcastManager.getInstance(applicationContext).sendBroadcast(intent)
    }

    override fun onDeletedMessages() {
        super.onDeletedMessages()
        Log.d("FCM", "Messages deleted")
    }

}