package com.kosalaamInc.kosalaam.feature.main.prayerRoomFragment.restaurantInfo

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.GetTokenResult
import com.kosalaamInc.kosalaam.global.Application
import com.kosalaamInc.kosalaam.model.network.response.RestauarntResponse
import com.kosalaamInc.kosalaam.repository.LikeRepository
import com.kosalaamInc.kosalaam.repository.SearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RestaurantInfoViewModel @Inject constructor(
    private val searchRepository : SearchRepository,
    private val likeRepository : LikeRepository
) : ViewModel(){
    private val _restaurantData = MutableLiveData<RestauarntResponse>()
    val restaurantData : MutableLiveData<RestauarntResponse> get() = _restaurantData

    private val _likeData = MutableLiveData<Boolean>()
    val likeData : MutableLiveData<Boolean> get() = _likeData
    fun getRestaurantInfo(id : String?) {
        var token : String? = null
        Application.user!!.getIdToken(true)
            .addOnCompleteListener(object : OnCompleteListener<GetTokenResult?> {
                override fun onComplete(task: Task<GetTokenResult?>) {
                    if (task.isSuccessful()) {
                        token = task.result!!.token.toString()
                        Log.d("token2",task.result!!.token.toString())
                        CoroutineScope(Dispatchers.IO).launch {
                            searchRepository.restaurantInfo("Bearer "+token,id!!).let {
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
            })
    }

    fun restaurantLike(id : String?){
        var token : String? = null
        Application.user!!.getIdToken(true)
            .addOnCompleteListener(object : OnCompleteListener<GetTokenResult?> {
                override fun onComplete(task: Task<GetTokenResult?>) {
                    if (task.isSuccessful()) {
                        token = task.result!!.token.toString()
                        Log.d("token2",task.result!!.token.toString())
                        CoroutineScope(Dispatchers.IO).launch {
                            likeRepository.restaurantLike("Bearer "+token,id!!).let {
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
                }
            })
    }

    fun restaurantLikeCancel(id : String?){
        var token : String? = null
        Application.user!!.getIdToken(true)
            .addOnCompleteListener(object : OnCompleteListener<GetTokenResult?> {
                override fun onComplete(task: Task<GetTokenResult?>) {
                    if (task.isSuccessful()) {
                        token = task.result!!.token.toString()
                        CoroutineScope(Dispatchers.IO).launch {
                            likeRepository.restaurantLikeCancel("Bearer "+token,id!!).let {
                                if(it.isSuccessful){
                                    likeData.postValue(false)
                                    Log.d("hotellikecancel","Success")
                                }
                                else{
                                    Log.d("hotellikecancel","fail")
                                }
                            }
                        }
                        Log.d("token2",task.result!!.token.toString())
                    }
                }
            })

    }
}