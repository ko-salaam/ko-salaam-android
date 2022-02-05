package com.kosalaamInc.kosalaam.model.network

import android.util.Log
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.kosalaamInc.kosalaam.global.Application
import com.kosalaamInc.kosalaam.model.network.api.KosalaamAPI
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException


object RetrofitClient{
//    val interceptor: AccessTokenInterceptor = AccessTokenInterceptor()
//    val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

    var client: OkHttpClient = OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor()!!).build()
    private val retrofit : Retrofit = Retrofit.Builder()
        .baseUrl(Application.BASE_URL)
        .addConverterFactory(NullOnEmptyConverterFactory())
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()
    val gson : Gson =   GsonBuilder().setLenient().create()
    private val retrofitAdd : Retrofit = Retrofit.Builder()
        .baseUrl(Application.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
//        .client(client)
        .build()

     val APIservice : KosalaamAPI = retrofit.create(KosalaamAPI::class.java)

    val APIserviceAdd : KosalaamAPI = retrofitAdd.create(KosalaamAPI::class.java)

    class AccessTokenInterceptor : Interceptor {
        @Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain) : Response = with(chain) {
            val token =""
            val newRequest = request().newBuilder()
                .addHeader("Authorization", token)
                .build()

            proceed(newRequest)
        }
    }

    private fun httpLoggingInterceptor(): HttpLoggingInterceptor? {
        val interceptor = HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
            override fun log(message: String) {
                Log.e("MyGitHubData :", message + "")
            }
        })
        return interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
    }
}
