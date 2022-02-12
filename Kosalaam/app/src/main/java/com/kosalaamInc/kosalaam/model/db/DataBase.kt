package com.kosalaamInc.kosalaam.model.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.kosalaamInc.kosalaam.model.data.RecentSearchData
import com.kosalaamInc.kosalaam.model.db.search.RecentSearchDao

@Database(entities = [RecentSearchData::class], version = 3, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun recentSearchDao(): RecentSearchDao

    companion object {
        val DATABASE_NAME = "KoSalaam-db"
    }
}