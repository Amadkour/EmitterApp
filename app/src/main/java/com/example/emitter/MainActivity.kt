package com.example.emitter

import android.content.*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AlertDialog
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed

import androidx.compose.material.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import com.example.firstproject.dataaccesslayer.viewModel.UserViewModel

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val intent: IntentFilter= IntentFilter("com.madkour.middleMan")
        registerReceiver(MyReceiver(),intent)
        setContent {
            val userModel: UserViewModel = UserViewModel()
            userModel.getAllUsers()
            val users by userModel.users.collectAsState()
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                itemsIndexed(users) { _, item ->
                    Row(
                        horizontalArrangement = Arrangement.SpaceAround,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                val builder = AlertDialog.Builder(this@MainActivity)
                                builder
                                    .setMessage("Are you sure you want to Send this item")
                                    .setCancelable(false)
                                    .setPositiveButton("Yes") { dialog, id ->

                                        val middleManIntent = Intent()
                                        //---------------------(send data item to MiddleMan App)-----------//
                                        middleManIntent.putExtra(
                                            "item",item)
                                        middleManIntent.action = "com.madkour.emitter"
                                        applicationContext.sendBroadcast(middleManIntent)
                                        //---------------------(pop MiddleMan app to  front)-----------//
                                        middleManIntent.flags = Intent.FLAG_INCLUDE_STOPPED_PACKAGES
                                        middleManIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                                        middleManIntent.action = "android.intent.action.VIEW"
                                        middleManIntent.component =
                                            ComponentName.unflattenFromString("com.example.middleman/com.example.middleman.MainActivity")
                                        startActivity(middleManIntent)
                                    }
                                    .setNegativeButton("No") { dialog, id ->
                                        // Dismiss the dialog
                                        dialog.dismiss()
                                    }
                                val alert = builder.create()
                                alert.show()

                            }
                    ) {

                        Text(
                            text = item.username ?: "",
                            style = TextStyle(
                                color = Color.Black,
                                fontSize = 15.sp
                            ),
                            modifier = Modifier.fillMaxWidth(0.4F)
                        )
                        Text(
                            text = item.name.split(" ").first().toString() ?: "", style = TextStyle(
                                color = Color.Black,
                                fontSize = 15.sp
                            ),
                            modifier = Modifier.fillMaxWidth(0.3F)
                        )
                        Text(
                            text = item.email ?: "", style = TextStyle(
                                color = Color.Blue,
                                fontSize = 10.sp
                            ),
                            modifier = Modifier.fillMaxWidth(0.3F)
                        )


                    }
                }
            }
        }
//        val reciever=object :BroadcastReceiver(){
//            override fun onReceive(p0: Context?, p1: Intent?) {
//                TODO("Not yet implemented")
//            }
//
//        }
    }
}