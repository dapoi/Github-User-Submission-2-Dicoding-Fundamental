package com.dapoidev.ourgithub.model.retrofit

import com.dapoidev.ourgithub.model.UserDetail
import com.dapoidev.ourgithub.model.UserModel
import com.dapoidev.ourgithub.model.UserArray
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiEndPoint {
    @GET("search/users")
    @Headers("Authorization: token {your token}")
    fun getUserSearch(@Query("q") query: String): Call<UserArray>


    @GET("users/{username}")
    @Headers("Authorization: token {your token}")
    fun getUserDetail(
        @Path("username") username: String
    ): Call<UserDetail>


    @GET("users/{username}/followers")
    @Headers("Authorization: token {your token}")
    fun getUserFollowers(
        @Path("username") username: String
    ): Call<ArrayList<UserModel>>


    @GET("users/{username}/following")
    @Headers("Authorization: token {your token}")
    fun getUserFollowing(
        @Path("username") username: String
    ): Call<ArrayList<UserModel>>


}