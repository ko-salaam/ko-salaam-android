package com.kosalaamInc.kosalaam.model.db.search

import androidx.lifecycle.LiveData
import androidx.room.*
import com.kosalaamInc.kosalaam.model.data.RecentSearchData

@Dao
interface RecentSearchDao {
    @Query ("SELECT * FROM RecentSearchData ORDER BY id DESC")
    fun getAll() : LiveData<List<RecentSearchData>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(recentSearch: RecentSearchData)

    @Delete
    fun delete(recentSearch: RecentSearchData)

    @Update
    fun update(recentSearch: RecentSearchData)

    @Query("Delete FROM recentsearchdata")
    fun deleteAll()

}