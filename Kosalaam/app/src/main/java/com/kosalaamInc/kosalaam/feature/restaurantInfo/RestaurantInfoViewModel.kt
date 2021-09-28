package com.kosalaamInc.kosalaam.feature.restaurantInfo

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kosalaamInc.kosalaam.model.network.response.RestauarntResponse
import com.kosalaamInc.kosalaam.repository.SearchRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class RestaurantInfoViewModel : ViewModel(){
    private val _restaurantData = MutableLiveData<RestauarntResponse>()

    val restaurantData : MutableLiveData<RestauarntResponse> get() = _restaurantData
    fun getRestaurantInfo(id : Int) {
        CoroutineScope(Dispatchers.IO).launch {
            SearchRepository().restaurantInfo(id).let {
                if(it.isSuccessful){
                    restaurantData.value = it.body()
                }

                else{

                }
            }
        }
    }
}