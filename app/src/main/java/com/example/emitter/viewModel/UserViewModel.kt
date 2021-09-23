package com.example.firstproject.dataaccesslayer.viewModel

import androidx.lifecycle.ViewModel
import com.example.emitter.accessLayer.API.UserClient
import com.example.emitter.accessLayer.model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserViewModel: ViewModel() {
    private var _mutablelivedata = MutableStateFlow<List<User>>(mutableListOf())
    val users: StateFlow<List<User>> = _mutablelivedata.asStateFlow()
    fun getAllUsers() {
        UserClient().getInstance().getAllUsers()
            .enqueue(object : Callback<List<User>> {
                override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                    _mutablelivedata.value = response.body() ?: mutableListOf()
                }

                override fun onFailure(call: Call<List<User>>, t: Throwable) {
                }

            })
    }
}
