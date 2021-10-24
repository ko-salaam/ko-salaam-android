package com.kosalaamInc.kosalaam.feature.main.prayerRoomFragment.prayerRoomInfo

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.GetTokenResult
import com.kosalaamInc.kosalaam.global.Application
import com.kosalaamInc.kosalaam.model.network.response.PrayerRoomResponse
import com.kosalaamInc.kosalaam.model.network.response.RestauarntResponse
import com.kosalaamInc.kosalaam.repository.LikeRepository
import com.kosalaamInc.kosalaam.repository.SearchRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PrayerRoomInfoViewModel : ViewModel(){
    private val _prayerData = MutableLiveData<PrayerRoomResponse>()
    val prayerData : MutableLiveData<PrayerRoomResponse> get() = _prayerData

    private val _likeData = MutableLiveData<Boolean>()
    val likeData : MutableLiveData<Boolean> get() = _likeData

    fun getPrayerRoomInfo(id : String?) {
        var token : String? = null
        Log.d("UserData",Application.user!!.email.toString())
        Application.user!!.getIdToken(true)
            .addOnCompleteListener(object : OnCompleteListener<GetTokenResult?> {
                override fun onComplete(task: Task<GetTokenResult?>) {
                    if (task.isSuccessful()) {
                        token = task.result!!.token.toString()
                        Log.d("token2",task.result!!.token.toString())
                        CoroutineScope(Dispatchers.IO).launch {
                            SearchRepository().prayerRoomInfo("Bearer "+token,id!!).let {
                                Log.d("PrayerRoomSuccess",it.code().toString())
                                Log.d("PrayerRoomSuccess",it.message().toString())

                                if(it.isSuccessful){
                                    Log.d("PrayerRoomSuccess","success")
                                    prayerData.postValue(it.body())
                                }

                                else{

                                }
                            }
                        }
                    }
                }
            })
    }

    fun prayerRoomLike(id : String?){
        var token : String? = null
        Application.user!!.getIdToken(true)
            .addOnCompleteListener(object : OnCompleteListener<GetTokenResult?> {
                override fun onComplete(task: Task<GetTokenResult?>) {
                    if (task.isSuccessful()) {
                        token = task.result!!.token.toString()
                        Log.d("token2",task.result!!.token.toString())
                        CoroutineScope(Dispatchers.IO).launch {
                            LikeRepository().prayerRoomLike("Bearer "+token,id!!).let {
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

    fun prayerRoomLikeCancel(id : String?){
        var token : String? = null
        Application.user!!.getIdToken(true)
            .addOnCompleteListener(object : OnCompleteListener<GetTokenResult?> {
                override fun onComplete(task: Task<GetTokenResult?>) {
                    if (task.isSuccessful()) {
                        token = task.result!!.token.toString()
                        CoroutineScope(Dispatchers.IO).launch {
                            LikeRepository().prayerRoomLikeCancel("Bearer "+token,id!!).let {
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