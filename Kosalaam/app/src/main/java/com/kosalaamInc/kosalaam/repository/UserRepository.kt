package com.kosalaamInc.kosalaam.repository

import com.kosalaamInc.kosalaam.model.data.UserCertified
import com.kosalaamInc.kosalaam.model.data.UserData
import com.kosalaamInc.kosalaam.model.data.UserHost
import com.kosalaamInc.kosalaam.model.network.RetrofitClient
import com.kosalaamInc.kosalaam.model.network.api.KosalaamAPI
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.PartMap
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val apiService : KosalaamAPI
) {

    fun sendUser(token: String,query : String) = apiService.getUser(token,query)

    suspend fun signUp(token : String) = apiService.signUp(token)

    suspend fun authMe(token : String) = apiService.getAuthMe(token)

    suspend fun putUser(token : String , data : UserCertified) = apiService.putUserCertified(token,data)

    suspend fun userHost(token : String , data : UserHost) = apiService.putUserHost(token,data)

    suspend fun editUser(token : String, body : UserData) = apiService.putAuthMe(token,body)
}