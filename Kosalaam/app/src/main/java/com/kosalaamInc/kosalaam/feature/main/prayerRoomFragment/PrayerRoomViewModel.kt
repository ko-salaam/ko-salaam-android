package com.kosalaamInc.kosalaam.feature.main.prayerRoomFragment

import android.app.Application
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.lifecycle.*
import com.facebook.internal.Mutable
import com.kosalaamInc.kosalaam.model.data.RecentSearchData
import com.kosalaamInc.kosalaam.model.network.response.CommonResponse
import com.kosalaamInc.kosalaam.model.network.response.HotelResponse
import com.kosalaamInc.kosalaam.model.network.response.PrayerRoomResponse
import com.kosalaamInc.kosalaam.model.network.response.RestauarntResponse
import com.kosalaamInc.kosalaam.repository.RecentSearchRepository
import com.kosalaamInc.kosalaam.repository.SearchRepository
import com.kosalaamInc.kosalaam.util.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

// AndroidViewModel to ViewModel
// 


@HiltViewModel
class PrayerRoomViewModel @Inject constructor(
    private val recentRepository: RecentSearchRepository,
    private val searchRepository : SearchRepository
) : ViewModel() {
    // 일단 Room으로 진행
    private val _serviceFailEvent = MutableLiveData<Boolean>()

    private val _focus_et = MutableLiveData<Event<Boolean>>()
    private val _back_bt1 = MutableLiveData<Event<Boolean>>()
    private val _recentDelete_bt = MutableLiveData<Event<Boolean>>()
    private val _searchKey_bt = MutableLiveData<Event<Boolean>>()
    private val _redo_bt = MutableLiveData<Event<Boolean>>()
    private val _location_bt = MutableLiveData<Event<Boolean>>()
    private val _restaurantData = MutableLiveData<List<RestauarntResponse>>()
    private val _hotelData = MutableLiveData<List<HotelResponse>>()
    private val _prayerData = MutableLiveData<List<PrayerRoomResponse>>()
    private val _commonData = MutableLiveData<List<CommonResponse>>()

    val serviceFaileEvent : LiveData<Boolean> get() = _serviceFailEvent

    val focus_et: LiveData<Event<Boolean>> get() = _focus_et
    val back_bt: LiveData<Event<Boolean>> get() = _back_bt1
    val redo_bt: LiveData<Event<Boolean>> get() = _redo_bt
    val recentDelete_bt: LiveData<Event<Boolean>> get() = _recentDelete_bt
    val location_bt: LiveData<Event<Boolean>> get() = _location_bt
    val searchKey_bt: LiveData<Event<Boolean>> get() = _searchKey_bt
    val restaurantData: MutableLiveData<List<RestauarntResponse>> get() = _restaurantData
    val hotelData: MutableLiveData<List<HotelResponse>> get() = _hotelData
    val prayerData: MutableLiveData<List<PrayerRoomResponse>> get() = _prayerData
    val commonData: MutableLiveData<List<CommonResponse>> get() = _commonData

//TODO    retrofit 통신 실패시

//    fun serviceFailEvent(){
//        _serviceFailEvent.value = false
//    }

    fun onFocusEvent() {
        _focus_et.value = Event(true)
    }

    fun onBack1Event() {
        _back_bt1.value = Event(true)
    }

    fun redoBtEvent() {
        _redo_bt.value = Event(true)
    }

    fun onRecentDeleteEvent() {
        _recentDelete_bt.value = Event(true)
    }

    fun searchKey(view: View, actionId: Int, event: KeyEvent?): Boolean {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            _searchKey_bt.value = Event(true)
            return true
        }
        return false
    }

    fun insert(recentSearch: RecentSearchData) {
        recentRepository.insert(recentSearch)
    }

    fun getAll(): LiveData<List<RecentSearchData>> {
        Log.d("displayCheck","this one")
        return recentRepository.getAll()
    //return items
    }

    fun deleteAll() {
        recentRepository.deleteAll()
    }

    fun getRestaurantSearch(
        domain: String?, distance: Int,
        keyword: String,
        latitude: Double,
        longitude: Double,
        muslimFriendly: ArrayList<String>?,
        pageNum: Int,
        pageSize: Int,
    ): MutableLiveData<List<RestauarntResponse>> {
        Log.d("Prayer", domain.toString())
        CoroutineScope(Dispatchers.IO).launch {
            searchRepository.searchRestaurant(distance,
                keyword,
                latitude,
                longitude,
                muslimFriendly,
                pageNum,
                pageSize).let {
                if (it.isSuccessful) {
                    if (it != null) {
                        restaurantData.postValue(it.body())
                    }
                } else {

                }
            }
        }
        return restaurantData
    }

    fun getHotelSearch(
        domain: String?, muslimFriendly: Boolean, distance: Int,
        keyword: String,
        latitude: Double,
        longitude: Double,
        pageNum: Int,
        pageSize: Int,
    ): MutableLiveData<List<HotelResponse>> {
        CoroutineScope(Dispatchers.IO).launch {
            searchRepository.searchHotel(distance,
                muslimFriendly,
                keyword,
                latitude,
                longitude,
                pageNum,
                pageSize).let {
                if (it.isSuccessful) {
                    if (it != null) {
                        hotelData.postValue(it.body())
                    }
                } else {

                }
            }
        }
        return hotelData
    }

    fun getPrayerRoomSearch(
        domain: String?, distance: Int,
        keyword: String,
        latitude: Double,
        longitude: Double,
        pageNum: Int,
        pageSize: Int,
    ): MutableLiveData<List<PrayerRoomResponse>> {
        CoroutineScope(Dispatchers.IO).launch {
            searchRepository.searchPrayerRoom(distance,
                keyword,
                latitude,
                longitude,
                pageNum,
                pageSize).let {
                if (it.isSuccessful) {
                    if (it != null) {
                        prayerData.postValue(it.body())
                    }
                } else {

                }
            }
        }
        return prayerData
    }

    fun getCommonSearch(
        domain: String?, muslimFriendly: Boolean, distance: Int,
        keyword: String,
        latitude: Double,
        longitude: Double,
        pageNum: Int,
        pageSize: Int,
    ): MutableLiveData<List<CommonResponse>> {
        CoroutineScope(Dispatchers.IO).launch {
            searchRepository.searchCommon(distance,
                muslimFriendly,
                keyword,
                latitude,
                longitude,
                pageNum,
                pageSize).let {
                Log.d("prayerRoom", it.code().toString())
                if (it.isSuccessful) {
                    if (it != null) {
                        commonData.postValue(it.body())
                    }
                } else {

                }
            }
        }
        return commonData
    }

    fun getCurrentLocation() {
        _location_bt.value = Event(true)
    }

}