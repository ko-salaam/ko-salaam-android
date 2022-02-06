package com.kosalaamInc.kosalaam.model.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.kosalaamInc.kosalaam.model.data.RecentSearchData
//import com.kosalaamInc.kosalaam.model.db.search.RecentSearch
import com.kosalaamInc.kosalaam.model.db.search.RecentSearchDao

@Database(entities = [RecentSearchData::class], version = 3, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun recentSearchDao(): RecentSearchDao

    companion object {
        private var instance: AppDatabase? = null
        val DATABASE_NAME = "KoSalaam-db"

//        @Synchronized
//        fun getInstance(context: Context): AppDatabase? {
//            if (instance == null) {
//                synchronized(AppDatabase::class){
//                    instance = Room.databaseBuilder(
//                        context.applicationContext,
//                        AppDatabase::class.java,
//                        "database-name"
//                    ).fallbackToDestructiveMigration().build()
//                    // version migration시 data 전체 삭제
//                    // room version 2.4-beta upgrade시 자동 데이터 이전 가능 고려
//                }
//            }
//            return instance
//        }
    }
}