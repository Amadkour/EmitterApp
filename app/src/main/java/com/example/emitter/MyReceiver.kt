package com.example.emitter

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.emitter.accessLayer.model.User

class MyReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {

        if (intent.extras != null) {
            val status: String? = intent.getStringExtra("status")
            if (status != null) {
                print(status)
                showCallback(status,context)
            }

        }
    }
    fun showCallback(text:String,context:Context){
        val builder = AlertDialog.Builder(context)
        builder
            .setMessage("Status is: $text")
            .setCancelable(false)
            .setPositiveButton("Yes") { dialog, id ->
                dialog.dismiss()
            }

        val alert = builder.create()
        alert.show()
    }
}