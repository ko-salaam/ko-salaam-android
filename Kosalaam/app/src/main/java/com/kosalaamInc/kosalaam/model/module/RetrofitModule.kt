package com.kosalaamInc.kosalaam.model.module

import com.kosalaamInc.kosalaam.global.Application
import com.kosalaamInc.kosalaam.model.network.NullOnEmptyConverterFactory
import com.kosalaamInc.kosalaam.model.network.api.KosalaamAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    @Singleton
    @Provides
    fun provideRetrofitInstance(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Application.BASE_URL)
            .client(okHttpClient)
            .client(provideHttpClient())
            .addConverterFactory(provideEmptyResponseFactory())
            .addConverterFactory(gsonConverterFactory)
            .build()
    }

    @Provides
    @Singleton
    fun provideKosalaamApiService(retrofit: Retrofit): KosalaamAPI {
        return retrofit.create(KosalaamAPI::class.java)
    }

    @Provides
    @Singleton
    fun provideHttpClient() : OkHttpClient{
        return OkHttpClient.Builder()
            .readTimeout(10, TimeUnit.SECONDS)
            .connectTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .addInterceptor(getLoggingInterceptor())
            .build()
    }

    private fun getLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }

    @Provides
    @Singleton
    fun provideConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }

    @Provides
    @Singleton
    fun provideEmptyResponseFactory() : NullOnEmptyConverterFactory{
        return NullOnEmptyConverterFactory()
    }
}