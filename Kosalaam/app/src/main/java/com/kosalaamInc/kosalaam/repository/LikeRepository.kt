package com.kosalaamInc.kosalaam.repository

import com.kosalaamInc.kosalaam.model.network.RetrofitClient

class LikeRepository {

    private val client = RetrofitClient.APIservice

    suspend fun hotelLike(authorization : String?,id : String) = client.hotelLike(authorization,id)

    suspend fun hotelLikeCancel(authorization : String?,id : String) = client.hotelLikeCancel(authorization,id)

    suspend fun prayerRoomLike(authorization : String?,id : String) = client.prayerLike(authorization,id)

    suspend fun prayerRoomLikeCancel(authorization : String?,id : String) = client.prayerLikeCancel(authorization,id)

    suspend fun restaurantLike(authorization : String?,id : String) = client.restaurantLike(authorization,id)

    suspend fun restaurantLikeCancel(authorization : String?,id : String) = client.restaurantLikeCancel(authorization,id)
}