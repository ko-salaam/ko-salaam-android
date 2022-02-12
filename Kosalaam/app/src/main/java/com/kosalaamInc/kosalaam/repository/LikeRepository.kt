package com.kosalaamInc.kosalaam.repository

import com.kosalaamInc.kosalaam.model.network.RetrofitClient
import com.kosalaamInc.kosalaam.model.network.api.KosalaamAPI
import javax.inject.Inject

class LikeRepository @Inject constructor(private val apiService : KosalaamAPI) {


    suspend fun hotelLike(authorization : String?,id : String) = apiService.hotelLike(authorization,id)

    suspend fun hotelLikeCancel(authorization : String?,id : String) = apiService.hotelLikeCancel(authorization,id)

    suspend fun prayerRoomLike(authorization : String?,id : String) = apiService.prayerLike(authorization,id)

    suspend fun prayerRoomLikeCancel(authorization : String?,id : String) = apiService.prayerLikeCancel(authorization,id)

    suspend fun restaurantLike(authorization : String?,id : String) = apiService.restaurantLike(authorization,id)

    suspend fun restaurantLikeCancel(authorization : String?,id : String) = apiService.restaurantLikeCancel(authorization,id)
}