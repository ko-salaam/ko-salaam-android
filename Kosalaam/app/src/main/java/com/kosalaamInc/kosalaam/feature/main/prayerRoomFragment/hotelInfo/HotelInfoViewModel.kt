package com.kosalaamInc.kosalaam.feature.main.prayerRoomFragment.hotelInfo

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.GetTokenResult
import com.kosalaamInc.kosalaam.global.Application
import com.kosalaamInc.kosalaam.model.network.response.HotelResponse
import com.kosalaamInc.kosalaam.model.network.response.RestauarntResponse
import com.kosalaamInc.kosalaam.repository.LikeRepository
import com.kosalaamInc.kosalaam.repository.SearchRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HotelInfoViewModel : ViewModel(){
    private val _hotelData = MutableLiveData<HotelResponse>()
    private val _likeData = MutableLiveData<Boolean>()
    val hotelData : MutableLiveData<HotelResponse> get() = _hotelData
    val likeData : MutableLiveData<Boolean> get() = _likeData

    fun getHotelInfo(id : String?) {
        CoroutineScope(Dispatchers.IO).launch {
            SearchRepository().hotelInfo(Application().getToken(),id!!).let {
                if(it.isSuccessful){
                    hotelData.postValue(it.body())
                }

                else{
                }
            }
        }
    }
    fun hotelLike(id : String?){
        CoroutineScope(Dispatchers.IO).launch {
            LikeRepository().hotelLike(Application().getToken(),id!!).let {
                if(it.isSuccessful){
                    likeData.postValue(true)

                    Log.d("hotellike","Success")
                }
                else{
                    Log.d("hotellike","fail"+it.code().toString())
                    Log.d("hotellike","fail")
                }
            }
        }
    }
    fun hotelLikeCancel(id : String?){
        CoroutineScope(Dispatchers.IO).launch {
            LikeRepository().hotelLikeCancel(Application().getToken(),id!!).let {
                if(it.isSuccessful){
                    likeData.postValue(false)
                    Log.d("hotellikecancel","Success")
                }
                else{
                    Log.d("hotellikecancel","fail")
                }
            }
        }
    }
    fun getToken() : String?{
        var token : String? = null
        Application.user!!.getIdToken(true)
            .addOnCompleteListener(object : OnCompleteListener<GetTokenResult> {
                override fun onComplete(p0: Task<GetTokenResult>) {
                    if(p0.isSuccessful){
                        token= p0.getResult().token
                    }
                    else{
                        token = null
                    }
                }

            })
        return token
    }
}