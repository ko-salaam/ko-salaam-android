package com.kosalaamInc.kosalaam.repository

import com.kosalaamInc.kosalaam.model.data.UserCertified
import com.kosalaamInc.kosalaam.model.data.UserData
import com.kosalaamInc.kosalaam.model.network.RetrofitClient

class UserRepository {
    private val userClient = RetrofitClient.APIservice
    private val user2Client = RetrofitClient.APIserviceAdd

    fun sendUser(token: String,query : String) = userClient.getUser(token,query)

    suspend fun signUp(token : String) = userClient.signUp(token)

    suspend fun authMe(token : String) = userClient.getAuthMe(token)

    suspend fun putUser(token : String , data : UserData) = userClient.putUserCertified(token,data)
}