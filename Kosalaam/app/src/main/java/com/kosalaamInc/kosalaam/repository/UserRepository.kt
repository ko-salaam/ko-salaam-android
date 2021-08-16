package com.kosalaamInc.kosalaam.repository

import com.kosalaamInc.kosalaam.model.network.RetrofitClient

class UserRepository {
    private val userClient = RetrofitClient.APIservice

    fun sendUser(token: String,query : String) = userClient.getUser(token,query)
}