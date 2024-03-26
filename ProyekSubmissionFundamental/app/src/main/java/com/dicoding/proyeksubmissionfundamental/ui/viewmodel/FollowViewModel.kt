package com.dicoding.proyeksubmissionfundamental.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.proyeksubmissionfundamental.data.response.ItemsItem
import com.dicoding.proyeksubmissionfundamental.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowViewModel : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _username = MutableLiveData<List<ItemsItem>>()
    val username: LiveData<List<ItemsItem>> = _username

    private val _toastMessage = MutableLiveData<String>()
    val toastMessage: LiveData<String> = _toastMessage

    private val _follow = MutableLiveData<List<ItemsItem>>()
    val followers: LiveData<List<ItemsItem>> = _follow

    private val _following = MutableLiveData<List<ItemsItem>>()
    val following: LiveData<List<ItemsItem>> = _following

    companion object {
        private const val USERNAME = ""
        private const val TAG = "FollowViewModel"
    }

    private fun showToast(message: String) {
        _toastMessage.value = message
    }

    init {
        listFollowers(USERNAME)
        listFollowing(USERNAME)
    }

    fun listFollowers(followers : String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getFollowers(followers)
        client.enqueue(object : Callback<List<ItemsItem>> {
            override fun onResponse(
                call: Call<List<ItemsItem>>,
                response: Response<List<ItemsItem>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful)      {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _follow.value = responseBody
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                _isLoading.value = false
                showToast("Network error occurred: ${t.message}")
            }
        })
    }

    fun listFollowing(followers : String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getFollowing(followers)
        client.enqueue(object : Callback<List<ItemsItem>> {
            override fun onResponse(
                call: Call<List<ItemsItem>>,
                response: Response<List<ItemsItem>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful)      {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _following.value = responseBody
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                _isLoading.value = false
                showToast("Network error occurred: ${t.message}")
            }
        })
    }
}