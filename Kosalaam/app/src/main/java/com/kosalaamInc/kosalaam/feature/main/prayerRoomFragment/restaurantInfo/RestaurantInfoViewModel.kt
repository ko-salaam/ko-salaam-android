package com.kosalaamInc.kosalaam.feature.main.prayerRoomFragment.restaurantInfo

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kosalaamInc.kosalaam.model.network.response.RestauarntResponse
import com.kosalaamInc.kosalaam.repository.SearchRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RestaurantInfoViewModel : ViewModel(){
    private val _restaurantData = MutableLiveData<RestauarntResponse>()
    val restaurantData : MutableLiveData<RestauarntResponse> get() = _restaurantData
    fun getRestaurantInfo(id : String?) {
        CoroutineScope(Dispatchers.IO).launch {
            SearchRepository().restaurantInfo(id!!).let {
                Log.d("PrayerRoomSuccess",it.code().toString())
                Log.d("PrayerRoomSuccess",it.message().toString())
                if(it.isSuccessful){
                    Log.d("PrayerRoomSuccess","success")
                    restaurantData.postValue(it.body())
                }

                else{

                }
            }
        }
    }
}