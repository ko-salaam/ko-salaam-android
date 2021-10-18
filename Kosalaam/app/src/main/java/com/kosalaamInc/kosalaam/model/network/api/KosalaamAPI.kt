package com.kosalaamInc.kosalaam.model.network.api

import com.kosalaamInc.kosalaam.model.network.response.*
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
    suspend fun getRestaurantList(@Query("distance") distance : Int , @Query("keyword") keyword : String, @Query("latitude") lat : Double, @Query("longitude")
    lon : Double, @Query("muslimFreindlies") muslimFriendly : ArrayList<String>?, @Query("pageNum") pageNum : Int, @Query("pageSize") pageSize : Int) :
    Response<List<RestauarntResponse>>
    // restaurant info

    @GET("/api/restaurant/{id}")
    suspend fun getRestaurantInfo(@Path("id") id : String?) : Response<RestauarntResponse>

    @GET("/api/accommodation")
    suspend fun getHotelList(@Query("distance") distance : Int, @Query("isMuslimFriendly") isMuslimFreindly : Boolean, @Query("keyword") keyword : String, @Query("latitude") lat : Double, @Query("longitude")
    lon : Double, @Query("pageNum") pageNum : Int, @Query("pageSize") pageSize : Int) :
            Response<List<HotelResponse>>

    @GET("/api/accommodation/{id}")
    fun getHotelInfo(@Path("id") id : Int) : Response<HotelResponse>

    @GET("/api/prayerroom")
    suspend fun getPrayerRoomList(@Query("distance") distance : Int , @Query("keyword") keyword : String, @Query("latitude") lat : Double, @Query("longitude")
    lon : Double, @Query("pageNum") pageNum : Int, @Query("pageSize") pageSize : Int) :
            Response<List<PrayerRoomResponse>>

    @GET("/api/prayerroom/{id}")
    fun getPrayerRoomInfo(@Path("id") id : Int) : Response<PrayerRoomResponse>

    @GET("/api/common")
    suspend fun getCommonList(@Query("distance") distance : Int ,@Query("isMuslimFriendly") isMuslimFreindly : Boolean, @Query("keyword") keyword : String, @Query("latitude") lat : Double, @Query("longitude")
    lon : Double, @Query("pageNum") pageNum : Int, @Query("pageSize") pageSize : Int) :
            Response<List<CommonResponse>>

    @GET("/api/common/{id}")
    fun getCommonInfo(@Path("id") id : Int) : Response<CommonResponse>

    //
    @POST("/api/restaurant")
    fun registerResturant()

}