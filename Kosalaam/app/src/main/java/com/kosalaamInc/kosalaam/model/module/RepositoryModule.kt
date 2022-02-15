package com.kosalaamInc.kosalaam.model.module

import com.kosalaamInc.kosalaam.model.network.api.KosalaamAPI
import com.kosalaamInc.kosalaam.repository.LikeRepository
import com.kosalaamInc.kosalaam.repository.PrayerRoomHostRepository
import com.kosalaamInc.kosalaam.repository.SearchRepository
import com.kosalaamInc.kosalaam.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule{

    @Singleton
    @Provides
    fun provideSearchRepository(apiService:KosalaamAPI)= SearchRepository(apiService)

    @Singleton
    @Provides
    fun provideLikeRepository(apiService:KosalaamAPI)= LikeRepository(apiService)

    @Singleton
    @Provides
    fun provideUserRespository(apiService: KosalaamAPI) = UserRepository(apiService)

    @Singleton
    @Provides
    fun providePrayerRoomHostRepository(apiService : KosalaamAPI) = PrayerRoomHostRepository(apiService)
}