package com.dicoding.proyeksubmissionfundamental.data.retrofit

import com.dicoding.proyeksubmissionfundamental.BuildConfig
import com.dicoding.proyeksubmissionfundamental.data.response.DetailUserResponse
import com.dicoding.proyeksubmissionfundamental.data.response.ItemsItem
import com.dicoding.proyeksubmissionfundamental.data.response.ListResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("search/users")
    @Headers("Authorization: token" + BuildConfig.API_KEY)
    fun getList(
        @Query("q") q : String
    ) : Call<ListResponse>

    @GET("users/{username}")
    @Headers("Authorization: token" + BuildConfig.API_KEY)
    fun getDetailUser(@Path("username") username: String): Call<DetailUserResponse>

    @GET("users/{username}/followers")
    @Headers("Authorization: token" + BuildConfig.API_KEY)
    fun getFollowers(@Path("username") username: String): Call<List<ItemsItem>>

    @GET("users/{username}/following")
    @Headers("Authorization: token" + BuildConfig.API_KEY)
    fun getFollowing(@Path("username") username: String): Call<List<ItemsItem>>
}