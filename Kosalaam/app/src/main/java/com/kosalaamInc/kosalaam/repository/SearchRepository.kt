package com.kosalaamInc.kosalaam.repository

import com.kosalaamInc.kosalaam.model.network.RetrofitClient
import com.kosalaamInc.kosalaam.model.network.api.KosalaamAPI
import javax.inject.Inject

class SearchRepository @Inject constructor(
   private val apiService : KosalaamAPI
){

    suspend fun searchRestaurant(distance : Int,
                       keyword : String,
                       latitude : Double,
                       longitude : Double,
                                 muslimFriendlies : ArrayList<String>?,
                       pageNum : Int,
                       pageSize : Int) =
        apiService.getRestaurantList(distance,keyword,latitude,longitude,muslimFriendlies,pageNum,pageSize)

    suspend fun restaurantInfo(authorization : String?,id : String?) = apiService.getRestaurantInfo(authorization,id)

    suspend fun searchHotel(distance : Int,
                            isMuslimFriendly : Boolean,
                                 keyword : String,
                                 latitude : Double,
                                 longitude : Double,
                                 pageNum : Int,
                                 pageSize : Int) =
        apiService.getHotelList(distance,isMuslimFriendly,keyword,latitude,longitude,pageNum,pageSize)

    suspend fun hotelInfo(authorization : String?,id : String) = apiService.getHotelInfo(authorization,id)

    suspend fun searchPrayerRoom(distance : Int,
                            keyword : String,
                            latitude : Double,
                            longitude : Double,
                            pageNum : Int,
                            pageSize : Int) =
        apiService.getPrayerRoomList(distance,keyword,latitude,longitude,pageNum,pageSize)

    suspend fun prayerRoomInfo(authorization : String?,id : String) = apiService.getPrayerRoomInfo(authorization,id)

    suspend fun searchCommon(distance : Int,
                             isMuslimFriendly : Boolean,
                                 keyword : String,
                                 latitude : Double,
                                 longitude : Double,
                                 pageNum : Int,
                                 pageSize : Int) =
        apiService.getCommonList(distance,isMuslimFriendly,keyword,latitude,longitude,pageNum,pageSize)




}