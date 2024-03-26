package com.dicoding.proyeksubmissionfundamental.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.proyeksubmissionfundamental.data.response.ItemsItem
import com.dicoding.proyeksubmissionfundamental.data.response.ListResponse
import com.dicoding.proyeksubmissionfundamental.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _username = MutableLiveData<List<ItemsItem>>()
    val username: LiveData<List<ItemsItem>> = _username

    private val _toastMessage = MutableLiveData<String>()

    companion object {
        private const val LOGIN_NAME = "Arif"
        private const val TAG = "MainViewModel"
    }

    private fun showToast(message: String) {
        _toastMessage.value = message
    }

    init {
        listUser(LOGIN_NAME)
    }

    fun listUser(query : String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getList(query)
        client.enqueue(object : Callback<ListResponse> {
            override fun onResponse(
                call: Call<ListResponse>,
                response: Response<ListResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful)      {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _username.value = response.body()?.items
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<ListResponse>, t: Throwable) {
                _isLoading.value = false
                showToast("Network error occurred: ${t.message}")
            }
        })
    }
}