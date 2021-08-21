package com.kosalaamInc.kosalaam.model.network.api

import com.kosalaamInc.kosalaam.model.network.response.RestauarntResponse
import retrofit2.Call
import retrofit2.http.*

interface KosalaamAPI {


    @GET("/api/user/{uid}")
    fun getUser(@Header("Authorization") authorization : String ,@Path("uid") uid : String)

    // restaurant list
    @GET("/api/restaurant")
    fun getRestaurantList(@Query("latitude") lat : String ,@Query("longitude")
    lon : String, @Query("pageNum") pageNum : Int, @Query("pageSize") pageSize : Int) :
    Call<List<RestauarntResponse>>
    // restaurant info

    @GET("/api/restaurant/{id}}")
    fun getRestaurantInfo(@Path("id") id : String) : Call<RestauarntResponse>

    //
    @POST("/api/restaurant")
    fun registerResturant()

}