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
        var token : String? = null
        Application.user!!.getIdToken(true)
            .addOnCompleteListener(object : OnCompleteListener<GetTokenResult?> {
                override fun onComplete(task: Task<GetTokenResult?>) {
                    if (task.isSuccessful()) {
                        token = task.result!!.token.toString()
                        Log.d("token2",task.result!!.token.toString())
                        CoroutineScope(Dispatchers.IO).launch {
                            SearchRepository().hotelInfo("Bearer "+token,id!!).let {
                                Log.d("tokentest2","Bearer "+getToken())
                                if(it.isSuccessful){
                                    hotelData.postValue(it.body())
                                }

                                else{

                                }
                            }
                        }
                    }
                }
            })
    }

    fun hotelLike(id : String?){
        var token : String? = null
        Application.user!!.getIdToken(true)
            .addOnCompleteListener(object : OnCompleteListener<GetTokenResult?> {
                override fun onComplete(task: Task<GetTokenResult?>) {
                    if (task.isSuccessful()) {
                        token = task.result!!.token.toString()
                        Log.d("token2",task.result!!.token.toString())
                        CoroutineScope(Dispatchers.IO).launch {
                            LikeRepository().hotelLike("Bearer "+token,id!!).let {
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
    fun hotelLikeCancel(id : String?){
        var token : String? = null
        Application.user!!.getIdToken(true)
            .addOnCompleteListener(object : OnCompleteListener<GetTokenResult?> {
                override fun onComplete(task: Task<GetTokenResult?>) {
                    if (task.isSuccessful()) {
                        token = task.result!!.token.toString()
                        CoroutineScope(Dispatchers.IO).launch {
                            LikeRepository().hotelLikeCancel("Bearer "+token,id!!).let {
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
    fun getToken() : String?{
        var token : String? = null
        Application.user!!.getIdToken(true)
            .addOnCompleteListener(object : OnCompleteListener<GetTokenResult?> {
                override fun onComplete(task: Task<GetTokenResult?>) {
                    if (task.isSuccessful()) {
                        token = task.result!!.token.toString()
                        Log.d("token2",task.result!!.token.toString())
                    }
                }
            })

        return token
    }
}