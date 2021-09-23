package com.example.emitter

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.example.emitter.accessLayer.model.User

class MyReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {

        if (intent.extras != null) {
            val user: User? = intent.getParcelableExtra<User>("item")
            Toast.makeText(context, user?.email+"  from Emitter", Toast.LENGTH_LONG).show()

        }
    }
}