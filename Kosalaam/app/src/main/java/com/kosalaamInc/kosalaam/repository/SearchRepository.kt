package com.kosalaamInc.kosalaam.repository

import com.kosalaamInc.kosalaam.model.network.RetrofitClient

class SearchRepository{
    private val searchClient = RetrofitClient.APIservice

    suspend fun searchRestaurant(distance : Int,
                       keyword : String,
                       latitude : Double,
                       longitude : Double,
                                 muslimFriendlies : ArrayList<String>?,
                       pageNum : Int,
                       pageSize : Int) =
        searchClient.getRestaurantList(distance,keyword,latitude,longitude,muslimFriendlies,pageNum,pageSize)

    suspend fun restaurantInfo(authorization : String?,id : String?) = searchClient.getRestaurantInfo(authorization,id)

    suspend fun searchHotel(distance : Int,
                            isMuslimFriendly : Boolean,
                                 keyword : String,
                                 latitude : Double,
                                 longitude : Double,
                                 pageNum : Int,
                                 pageSize : Int) =
        searchClient.getHotelList(distance,isMuslimFriendly,keyword,latitude,longitude,pageNum,pageSize)


    suspend fun hotelInfo(authorization : String?,id : String) = searchClient.getHotelInfo(authorization,id)

    suspend fun searchPrayerRoom(distance : Int,
                            keyword : String,
                            latitude : Double,
                            longitude : Double,
                            pageNum : Int,
                            pageSize : Int) =
        searchClient.getPrayerRoomList(distance,keyword,latitude,longitude,pageNum,pageSize)

    suspend fun prayerRoomInfo(authorization : String?,id : String) = searchClient.getPrayerRoomInfo(authorization,id)

    suspend fun searchCommon(distance : Int,
                             isMuslimFriendly : Boolean,
                                 keyword : String,
                                 latitude : Double,
                                 longitude : Double,
                                 pageNum : Int,
                                 pageSize : Int) =
        searchClient.getCommonList(distance,isMuslimFriendly,keyword,latitude,longitude,pageNum,pageSize)




}