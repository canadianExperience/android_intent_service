package com.me.android_intent_service

import android.app.IntentService
import android.content.Intent
import android.os.SystemClock
import androidx.localbroadcastmanager.content.LocalBroadcastManager

class MyIntentService : IntentService("MyIntentService") {

    override fun onHandleIntent(intent: Intent?) {
        intent?.let { sIntent ->
            val message = sIntent.getStringExtra("message")
            sIntent.action = FILTER_ACTION_KEY
            SystemClock.sleep(3000)
            val echoMessage = "IntentService after a pause of 3 seconds echoes: $message"

            LocalBroadcastManager.getInstance(applicationContext).sendBroadcast(
                sIntent.putExtra("broadcastMessage", echoMessage)
            )
        }
    }
}