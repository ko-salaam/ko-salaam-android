package com.kosalaamInc.kosalaam.feature.main.prayerRoomFragment

import android.app.Application
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kosalaamInc.kosalaam.model.data.RecentSearchData
import com.kosalaamInc.kosalaam.repository.RecentSearchRepository
import com.kosalaamInc.kosalaam.util.Event

class PrayerRoomViewModel(application : Application) : AndroidViewModel(application){

    private val _focus_et = MutableLiveData<Event<Boolean>>()
    private val _back_bt1 = MutableLiveData<Event<Boolean>>()
    private val _recentDelete_bt = MutableLiveData<Event<Boolean>>()
    private val _searchKey_bt = MutableLiveData<Event<Boolean>>()
    private val recentRepository = RecentSearchRepository(application)
    private val items = recentRepository.getAll()

    val focus_et: LiveData<Event<Boolean>> get() = _focus_et
    val back_bt : LiveData<Event<Boolean>> get() = _back_bt1
    val recentDelete_bt : LiveData<Event<Boolean>> get() = _recentDelete_bt
    val searchKey_bt : LiveData<Event<Boolean>> get() = _searchKey_bt

    fun onFocusEvent(){
        _focus_et.value = Event(true)
    }

    fun onBack1Event(){
        _back_bt1.value =Event(true)
    }

    fun onRecentDeleteEvent(){
        _recentDelete_bt.value =Event(true)

    }

    fun searchKey(view: View, actionId: Int, event: KeyEvent?):Boolean{
        if(actionId == EditorInfo.IME_ACTION_SEARCH) {
            _searchKey_bt.value=Event(true)
            return true
        }
        return false
    }

    fun insert(recentSearch : RecentSearchData){
        recentRepository.insert(recentSearch)
    }

    fun delete(recentSearch: RecentSearchData){
        recentRepository.delete(recentSearch)
    }

    fun getAll() : LiveData<List<RecentSearchData>>{
        return items
    }
    fun deleteAll(){

    }




}