package com.kosalaamInc.kosalaam.model.network.api

import com.kosalaamInc.kosalaam.model.network.response.HotelResponse
import com.kosalaamInc.kosalaam.model.network.response.RestauarntResponse
import com.kosalaamInc.kosalaam.model.network.response.SignInResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface KosalaamAPI {

    @GET("/api/user/{uid}")
    fun getUser(@Header("Authorization") authorization : String ,@Path("uid") uid : String)

    @POST("/api/auth")
    fun signIn(@Header("Authorization") authorization : String)

    @POST("/api/auth/new")
    suspend fun signUp(@Header("Authorization") authorization : String) :Response<SignInResponse>

    // restaurant list
    @GET("/api/restaurant")
    suspend fun getRestaurantList(@Query("distance") distance : Int, @Query("keyword") keyword : String, @Query("latitude") lat : Double, @Query("longitude")
    lon : Double, @Query("pageNum") pageNum : Int, @Query("pageSize") pageSize : Int) :
    Response<List<RestauarntResponse>>
    // restaurant info

    @GET("/api/restaurant/{id}")
    suspend fun getRestaurantInfo(@Path("id") id : Int) : Response<RestauarntResponse>

    @GET("/api/accommodation")
    suspend fun getHotelList(@Query("distance") distance : Int, @Query("keyword") keyword : String, @Query("latitude") lat : Double, @Query("longitude")
    lon : Double, @Query("pageNum") pageNum : Int, @Query("pageSize") pageSize : Int) :
            Response<List<HotelResponse>>

    @GET("/api/accommodation/{id}")
    fun getHotelInfo(@Path("id") id : Int) : Response<HotelResponse>

    //
    @POST("/api/restaurant")
    fun registerResturant()

}