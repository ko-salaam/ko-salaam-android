package com.kosalaamInc.kosalaam.repository

import com.kosalaamInc.kosalaam.model.network.RetrofitClient
import okhttp3.MultipartBody
import okhttp3.RequestBody

class PrayerRoomHostRepository {
    private val client = RetrofitClient.APIservice

    suspend fun registerPlayerRoom(authorization : String?, images: List<MultipartBody.Part>, data : HashMap<String,RequestBody>) = client.registerPlayerRoom(authorization,images,data)

}