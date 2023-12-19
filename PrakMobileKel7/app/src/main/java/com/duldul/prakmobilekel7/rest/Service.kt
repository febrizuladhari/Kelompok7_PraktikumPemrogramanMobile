package com.duldul.prakmobilekel7.rest

import com.duldul.prakmobilekel7.Model.Note
import com.duldul.prakmobilekel7.Model.ResponseResult
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface Service {
    @GET("json.php")
    fun getNote(): Call<List<Note>>

    @GET("insert.php")
    fun insertUser(
        @Query("title") title: String,
        @Query("note") note: String,
        @Query("color") color: String
    ): Call<ResponseResult>

    @GET("update.php")
    fun updateUser(
        @Query("id") id: String,
        @Query("title") title: String,
        @Query("note") note: String,
        @Query("color") color: String
    ): Call<ResponseResult>

    @GET("delete.php")
    fun deletetUser(@Query("id") id: String): Call<ResponseResult>
}