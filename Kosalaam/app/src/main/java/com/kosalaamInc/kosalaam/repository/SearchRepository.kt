package com.kosalaamInc.kosalaam.repository

import com.kosalaamInc.kosalaam.model.network.RetrofitClient

class SearchRepository{
    private val searchClient = RetrofitClient.APIservice

    suspend fun searchRestaurant(distance : Int,
                       keyword : String,
                       latitude : Double,
                       longitude : Double,
                       pageNum : Int,
                       pageSize : Int) =
        searchClient.getRestaurantList(distance,keyword,latitude,longitude,pageNum,pageSize)

    suspend fun restaurantInfo(id : String?) = searchClient.getRestaurantInfo(id)

    suspend fun searchHotel(distance : Int,
                                 keyword : String,
                                 latitude : Double,
                                 longitude : Double,
                                 pageNum : Int,
                                 pageSize : Int) =
        searchClient.getHotelList(distance,keyword,latitude,longitude,pageNum,pageSize)

    suspend fun hotelInfo(id : Int) = searchClient.getHotelInfo(id)
}