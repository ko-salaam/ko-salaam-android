package com.kosalaamInc.kosalaam.model.module

import android.content.Context
import androidx.room.Room
import com.kosalaamInc.kosalaam.model.db.AppDatabase
import com.kosalaamInc.kosalaam.model.db.search.RecentSearchDao
import com.kosalaamInc.kosalaam.repository.RecentSearchRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


// add dao repsitory module database name
@Module
@InstallIn(SingletonComponent::class)
class DataBaseModule {
    @Singleton
    @Provides
    fun provideKosalaamDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room
            .databaseBuilder(
                context,
                AppDatabase::class.java,
                AppDatabase.DATABASE_NAME)
            .build()
    }

    @Singleton
    @Provides
    fun provideRecentSearchDao(kosalaamDB: AppDatabase): RecentSearchDao {
        return kosalaamDB.recentSearchDao()
    }

    @Singleton
    @Provides
    fun provideRecentSearchRepository(recentSearchDao: RecentSearchDao): RecentSearchRepository {
        return RecentSearchRepository(recentSearchDao)
    }
}