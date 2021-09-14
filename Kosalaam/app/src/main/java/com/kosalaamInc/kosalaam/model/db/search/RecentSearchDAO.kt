package com.kosalaamInc.kosalaam.model.db.search

import androidx.lifecycle.LiveData
import androidx.room.*
import com.kosalaamInc.kosalaam.model.data.RecentSearchData

@Dao
interface RecentSearchDAO {
    @Query ("SELECT * FROM RecentSearchData")
    fun getAll() : LiveData<List<RecentSearchData>>

    @Insert
    fun insert(recentSearch: RecentSearchData)

    @Delete
    fun delete(recentSearch: RecentSearchData)

    @Update
    fun update(recentSearch: RecentSearchData)



}