package com.kosalaamInc.kosalaam.model.network

import com.kosalaamInc.kosalaam.global.Application
import com.kosalaamInc.kosalaam.model.network.api.KosalaamAPI
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

object RetrofitClient{
    val interceptor: AccessTokenInterceptor = AccessTokenInterceptor()
    val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

    private val retrofit : Retrofit = Retrofit.Builder()
        .baseUrl(Application.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
//        .client(client)
        .build()

    val APIservice : KosalaamAPI = retrofit.create(KosalaamAPI::class.java)

    class AccessTokenInterceptor : Interceptor {
        @Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain) : Response = with(chain) {
            val token =""
            val newRequest = request().newBuilder()
                .addHeader("Authorization",token)
                .build()

            proceed(newRequest)
        }
    }
}
