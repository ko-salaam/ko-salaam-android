package com.kosalaamInc.kosalaam.Model.Network

import com.kosalaamInc.kosalaam.Global.Application
import com.kosalaamInc.kosalaam.Model.Network.API.KosalaamAPI
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient{
    private val retrofit : Retrofit = Retrofit.Builder().
    baseUrl(Application.BASE_URL).
    addConverterFactory(GsonConverterFactory.create())
        .build()

    val APIservice : KosalaamAPI = retrofit.create(KosalaamAPI::class.java)
}