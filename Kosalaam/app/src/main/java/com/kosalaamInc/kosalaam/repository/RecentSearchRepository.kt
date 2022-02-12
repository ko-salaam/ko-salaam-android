package com.kosalaamInc.kosalaam.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.kosalaamInc.kosalaam.model.data.RecentSearchData
import com.kosalaamInc.kosalaam.model.db.AppDatabase
import com.kosalaamInc.kosalaam.model.db.search.RecentSearchDao
import javax.inject.Inject

class RecentSearchRepository @Inject constructor(
    private val recentSearchDao : RecentSearchDao){

    fun insert(recentSearch : RecentSearchData) {
        recentSearchDao.insert(recentSearch)
    }

    fun delete(recentSearch : RecentSearchData){
        recentSearchDao.delete(recentSearch)
    }

    fun getAll(): LiveData<List<RecentSearchData>> {
        return recentSearchDao.getAll()
    }

    fun deleteAll()  {
        recentSearchDao.deleteAll()
    }
}