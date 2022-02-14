package com.kosalaamInc.kosalaam.repository

import com.kosalaamInc.kosalaam.model.data.HostRegisterData
import com.kosalaamInc.kosalaam.model.network.RetrofitClient
import com.kosalaamInc.kosalaam.model.network.api.KosalaamAPI
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

class PrayerRoomHostRepository @Inject constructor(
    private val apiService : KosalaamAPI
) {

    suspend fun registerPlayerRoom(authorization : String?,images: List<MultipartBody.Part>,data : HashMap<String,RequestBody>) = apiService.registerPlayerRoom(authorization,data,images)

    suspend fun registerPlayerRoomdata(authorization: String?,id : String, data : HostRegisterData) = apiService.registerPlayerRoomData(authorization,id,data)

    suspend fun registerPlayerRoomdataTest(authorization: String?, data : HostRegisterData) = apiService.registerPrayerRoomTest(authorization,data)

}