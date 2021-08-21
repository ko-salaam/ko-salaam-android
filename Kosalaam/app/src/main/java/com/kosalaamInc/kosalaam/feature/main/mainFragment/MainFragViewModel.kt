package com.kosalaamInc.kosalaam.feature.main.mainFragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kosalaamInc.kosalaam.util.Event

class MainFragViewModel :ViewModel(){

    private val _prayerRoomBtEvent = MutableLiveData<Event<Boolean>>()
    private val _hotelBtEvent = MutableLiveData<Event<Boolean>>()
    private val _restaurantBtEvent = MutableLiveData<Event<Boolean>>()
    private val _prayerTimeBtEvent = MutableLiveData<Event<Boolean>>()
    private val _activitiesBtEvent = MutableLiveData<Event<Boolean>>()
    private val _newMagazineBtEvent = MutableLiveData<Event<Boolean>>()
    private val _mostViewBtEvent = MutableLiveData<Event<Boolean>>()
    private val _tourBtEvent = MutableLiveData<Event<Boolean>>()
    private val _foodBtEvent = MutableLiveData<Event<Boolean>>()
    private val _kpopBtEvent = MutableLiveData<Event<Boolean>>()
    private val _magazineList = MutableLiveData<ArrayList<MagazineData>>()


    val magzineList get( )= _magazineList
    val prayerRoom_BtEvent: LiveData<Event<Boolean>> get() = _prayerRoomBtEvent
    val hotel_BtEvent: LiveData<Event<Boolean>> get() = _hotelBtEvent
    val restaurant_BtEvent : LiveData<Event<Boolean>> get() = _restaurantBtEvent
    val prayerTime_BtEvent : LiveData<Event<Boolean>> get() = _prayerTimeBtEvent
    val activities_BtEvent : LiveData<Event<Boolean>> get() = _activitiesBtEvent
    val newMagazine_BtEvent : LiveData<Event<Boolean>> get() = _newMagazineBtEvent
    val mostView_BtEvent : LiveData<Event<Boolean>> get() = _mostViewBtEvent
    val tour_BtEvent : LiveData<Event<Boolean>> get() = _tourBtEvent
    val food_BtEvent : LiveData<Event<Boolean>> get() = _foodBtEvent
    val kpop_BtEvent : LiveData<Event<Boolean>> get() = _kpopBtEvent

    fun onPrayerRoomBtEvent() {
        _prayerRoomBtEvent.value = Event(true)
    }

    fun onHotelBtEvent() {
     _hotelBtEvent.value = Event(true)
    }

    fun onRestaurantBtEvent() {
        _restaurantBtEvent.value = Event(true)
    }

    fun onPrayTimeBtEvent() {
        _prayerTimeBtEvent.value = Event(true)
    }

    fun onActivitiesBtEvent(){
        _activitiesBtEvent.value = Event(true)
    }

    fun onNewMagazineBtEvent(){
        _newMagazineBtEvent.value = Event(true)
    }
    fun onMostViewBtEvent(){
        _mostViewBtEvent.value = Event(true)
    }
    fun onTourBtEvent(){
        _tourBtEvent.value = Event(true)
    }
    fun onFoodBtEvent(){
        _foodBtEvent.value = Event(true)
    }
    fun onKpopBtEvent(){
        _kpopBtEvent.value = Event(true)
    }

}


data class MagazineData (
    val post : String?
)
