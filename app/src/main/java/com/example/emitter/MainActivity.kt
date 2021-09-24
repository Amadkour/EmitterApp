package com.example.emitter

import android.content.*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AlertDialog
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape

import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.firstproject.dataaccesslayer.viewModel.UserViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val intent = IntentFilter("com.madkour.middleMan")
        registerReceiver(MyReceiver(), intent)

        setContent {
            val userModel: UserViewModel = UserViewModel()
            userModel.getAllUsers()
            val users by userModel.users.collectAsState()
            val isRefreshing by userModel.isRefreshing.collectAsState()
            val middleManIntent = Intent()
            SwipeRefresh(
                state = rememberSwipeRefreshState(isRefreshing),
                onRefresh = { userModel.getAllUsers() },
            ) {
                LazyColumn(modifier = Modifier.fillMaxSize()
                    .padding(5.dp)) {
                    itemsIndexed(users) { _, item ->
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 1.dp)
                                .clip(RoundedCornerShape(20))
                                .background(Color.Gray.copy(alpha = 0.1f))
                                .padding(10.dp)
                                .clickable {
                                    val builder = AlertDialog.Builder(this@MainActivity)
                                    builder
                                        .setMessage("Are you sure you want to Send this item")
                                        .setCancelable(false)
                                        .setPositiveButton("Yes") { dialog, id ->

                                            //---------------------(send data item to MiddleMan App)-----------//
                                            middleManIntent.putExtra("item", item)
                                            middleManIntent.action = "com.madkour.emitter"
                                            applicationContext.sendBroadcast(middleManIntent)
                                        }
                                        .setNegativeButton("No") { dialog, id ->
                                            // Dismiss the dialog
                                            dialog.dismiss()
                                        }
                                    val alert = builder.create()
                                    alert.show()

                                }
                        ) {
                            Row(modifier = Modifier.fillMaxWidth()) {
                                TextDesign(
                                    modifier = Modifier.weight(1F),
                                    false,
                                    item.name
                                )
                                TextDesign(
                                    modifier = Modifier.weight(1F),
                                    false,
                                    item.username,
                                )
                            }
                            TextDesign(modifier = Modifier, true, item.email)
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun TextDesign(modifier: Modifier, isEmail: Boolean, text: String) = Text(
        text = text,
        style = TextStyle(
            color = if (isEmail) Color.Blue else Color.Black,
            fontSize = if (isEmail) 10.sp else 15.sp
        ),
        modifier = modifier
    )

}