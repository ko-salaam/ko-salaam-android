package com.kosalaamInc.kosalaam.model.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.kosalaamInc.kosalaam.model.data.RecentSearchData
//import com.kosalaamInc.kosalaam.model.db.search.RecentSearch
import com.kosalaamInc.kosalaam.model.db.search.RecentSearchDAO

@Database(entities = [RecentSearchData::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun recentSearchDao(): RecentSearchDAO

    companion object {
        private var instance: AppDatabase? = null

        @Synchronized
        fun getInstance(context: Context): AppDatabase? {
            if (instance == null) {
                synchronized(AppDatabase::class){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "database-name"
                    ).fallbackToDestructiveMigration().build()
                }
            }
            return instance
        }
    }
}