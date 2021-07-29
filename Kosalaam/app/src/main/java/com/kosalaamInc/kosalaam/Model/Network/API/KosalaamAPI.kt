package com.kosalaamInc.kosalaam.Model.Network.API

import retrofit2.http.GET
import retrofit2.http.Query

interface KosalaamAPI {
    // restaurant list
    @GET("/api/restaurant")
    fun getRestaurantList(@Query("latitude") lat : String ,@Query("longitude") lon : String)
    // restaurant list
    @GET("/api/restaurant/info")
    fun getRestaurantInfo(@Query("id") id : String)

}