package com.kosalaamInc.kosalaam.feature.main.myPageFragment.hostInfo.hostInfoEdit

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.GetTokenResult
import com.kosalaamInc.kosalaam.global.Application
import com.kosalaamInc.kosalaam.model.network.response.UserResponse
import com.kosalaamInc.kosalaam.repository.PrayerRoomHostRepository
import com.kosalaamInc.kosalaam.repository.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody

class HostInfoEditViewModel :ViewModel(){
    private val _successData = MutableLiveData<Boolean>()
    val successData : MutableLiveData<Boolean> get() = _successData

//    fun getUserInfo(images : List<MultipartBody.Part>, data : HashMap<String, RequestBody>) {
//        var token : String? = null
//        Application.user!!.getIdToken(true)
//            .addOnCompleteListener(object : OnCompleteListener<GetTokenResult?> {
//                override fun onComplete(task: Task<GetTokenResult?>) {
//                    if (task.isSuccessful()) {
//                        token = task.result!!.token.toString()
//                        Log.d("token2",task.result!!.token.toString())
//                        CoroutineScope(Dispatchers.IO).launch {
//                            PrayerRoomHostRepository().registerPlayerRoom("Bearer "+token,images,data).let {
//                                if(it.isSuccessful){
//                                    successData.postValue(true)
//                                }
//                                else{
//
//                                }
//                            }
//                        }
//                    }
//                }
//            })
//    }
}