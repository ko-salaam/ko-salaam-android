package com.kosalaamInc.kosalaam.model.network.api

import com.kosalaamInc.kosalaam.model.data.UserCertified
import com.kosalaamInc.kosalaam.model.data.UserData
import com.kosalaamInc.kosalaam.model.network.response.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
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
    suspend fun getRestaurantInfo(@Header("Authorization") authorization : String?,@Path("id") id : String?) : Response<RestauarntResponse>

    @GET("/api/accommodation")
    suspend fun getHotelList(@Query("distance") distance : Int, @Query("isMuslimFriendly") isMuslimFreindly : Boolean, @Query("keyword") keyword : String, @Query("latitude") lat : Double, @Query("longitude")
    lon : Double, @Query("pageNum") pageNum : Int, @Query("pageSize") pageSize : Int) :
            Response<List<HotelResponse>>

    @GET("/api/accommodation/{id}")
    suspend fun getHotelInfo(@Header("Authorization") authorization : String?,@Path("id") id : String) : Response<HotelResponse>

    @GET("/api/prayerroom")
    suspend fun getPrayerRoomList(@Query("distance") distance : Int , @Query("keyword") keyword : String, @Query("latitude") lat : Double, @Query("longitude")
    lon : Double, @Query("pageNum") pageNum : Int, @Query("pageSize") pageSize : Int) :
            Response<List<PrayerRoomResponse>>

    @GET("/api/prayerroom/{id}")
    suspend fun getPrayerRoomInfo(@Header("Authorization") authorization : String?,@Path("id") id : String) : Response<PrayerRoomResponse>

    @GET("/api/common")
    suspend fun getCommonList(@Query("distance") distance : Int ,@Query("isMuslimFriendly") isMuslimFreindly : Boolean, @Query("keyword") keyword : String, @Query("latitude") lat : Double, @Query("longitude")
    lon : Double, @Query("pageNum") pageNum : Int, @Query("pageSize") pageSize : Int) :
            Response<List<CommonResponse>>

    @GET("/api/common/{id}")
    suspend fun getCommonInfo(@Path("id") id : String) : Response<CommonResponse>

    @POST("api/prayerroom/like/{id}")
    suspend fun prayerLike(@Header("Authorization") authorization : String?, @Path("id") id : String) : Response<BaseResponse>

    @DELETE("api/prayerroom/like/{id}")
    suspend fun prayerLikeCancel(@Header("Authorization") authorization : String?, @Path("id") id : String) : Response<BaseResponse>

    @POST("api/accommodation/like/{id}")
    suspend fun hotelLike(@Header("Authorization") authorization : String?, @Path("id") id : String) : Response<BaseResponse>

    @DELETE("api/accommodation/like/{id}")
    suspend fun hotelLikeCancel(@Header("Authorization") authorization : String?, @Path("id") id : String) : Response<BaseResponse>

    @POST("api/restaurant/like/{id}")
    suspend fun restaurantLike(@Header("Authorization") authorization : String?, @Path("id") id : String) : Response<BaseResponse>

    @DELETE("api/restaurant/like/{id}")
    suspend fun restaurantLikeCancel(@Header("Authorization") authorization : String?, @Path("id") id : String) : Response<BaseResponse>

    //
    @POST("/api/restaurant")
    suspend fun registerResturant()

    @GET("/api/auth/me")
    suspend fun getAuthMe(@Header("Authorization") authorization : String?) : Response<UserResponse>

    @Multipart
    @POST("api/post/prayerroom")
    suspend fun registerPlayerRoom(@Header("Authorization") authorization: String?, @Part images : List<MultipartBody.Part>, @PartMap data : HashMap<String,RequestBody>) : Response<PrayerRoomResponse>

    //@Headers("content-type: application/json")
    @PUT("api/auth")
    suspend fun putUserCertified(@Header("Authorization") authorization: String?, @Body user : UserData) : Response<UserResponse>
}