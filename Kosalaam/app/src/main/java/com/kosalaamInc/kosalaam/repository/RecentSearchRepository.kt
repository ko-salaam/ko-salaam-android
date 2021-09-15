package com.kosalaamInc.kosalaam.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.kosalaamInc.kosalaam.model.data.RecentSearchData
import com.kosalaamInc.kosalaam.model.db.AppDatabase
import com.kosalaamInc.kosalaam.model.db.search.RecentSearchDAO

class RecentSearchRepository(application: Application){
    private val recentSearchDao : RecentSearchDAO
    private val recentList : LiveData<List<RecentSearchData>>

    init{
        var db = AppDatabase.getInstance(application)
        recentSearchDao = db!!.recentSearchDao()
        recentList = db.recentSearchDao().getAll()
    }
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