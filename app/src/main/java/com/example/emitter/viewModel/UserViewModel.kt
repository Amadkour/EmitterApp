package com.example.firstproject.dataaccesslayer.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.emitter.accessLayer.API.UserClient
import com.example.emitter.accessLayer.model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserViewModel: ViewModel() {
    private val _isRefreshing = MutableStateFlow(false)

    val isRefreshing: StateFlow<Boolean>  get() = _isRefreshing.asStateFlow()

    private var _mutablelivedata = MutableStateFlow<List<User>>(mutableListOf())
    val users: StateFlow<List<User>> = _mutablelivedata.asStateFlow()
    fun getAllUsers() {
        viewModelScope.launch {
            _mutablelivedata.emit(listOf())
            // A fake 2 second 'refresh'
            _isRefreshing.emit(true)
            UserClient().getInstance().getAllUsers()
                .enqueue(object : Callback<List<User>> {
                    override fun onResponse(
                        call: Call<List<User>>,
                        response: Response<List<User>>
                    ) {
                        _mutablelivedata.value = response.body() ?: mutableListOf()

                    }

                    override fun onFailure(call: Call<List<User>>, t: Throwable) {
                    }

                })
            _isRefreshing.emit(false)
        }
    }
}
