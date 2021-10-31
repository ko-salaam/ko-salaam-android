package com.kosalaamInc.kosalaam.repository

import com.kosalaamInc.kosalaam.model.data.UserCertified
import com.kosalaamInc.kosalaam.model.data.UserData
import com.kosalaamInc.kosalaam.model.data.UserHost
import com.kosalaamInc.kosalaam.model.network.RetrofitClient
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.PartMap

class UserRepository {
    private val userClient = RetrofitClient.APIservice

    fun sendUser(token: String,query : String) = userClient.getUser(token,query)

    suspend fun signUp(token : String) = userClient.signUp(token)

    suspend fun authMe(token : String) = userClient.getAuthMe(token)

    suspend fun putUser(token : String , data : UserCertified) = userClient.putUserCertified(token,data)



    suspend fun userHost(token : String , data : UserHost) = userClient.putUserHost(token,data)

    suspend fun editUser(token : String, body : UserData) = userClient.putAuthMe(token,body)
}