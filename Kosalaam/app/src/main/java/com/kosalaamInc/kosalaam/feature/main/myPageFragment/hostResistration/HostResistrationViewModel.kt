package com.kosalaamInc.kosalaam.feature.main.myPageFragment.hostResistration

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.GetTokenResult
import com.kosalaamInc.kosalaam.global.Application
import com.kosalaamInc.kosalaam.model.data.HostRegisterData
import com.kosalaamInc.kosalaam.model.data.UserHost
import com.kosalaamInc.kosalaam.repository.PrayerRoomHostRepository
import com.kosalaamInc.kosalaam.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

@HiltViewModel
class HostResistrationViewModel @Inject constructor(
    private val userRepository : UserRepository
) : ViewModel(){
    private val _postPrayerRoom = MutableLiveData<Boolean>()
    val postPrayerRoom : MutableLiveData<Boolean> get() = _postPrayerRoom

    fun postPrayerRoomInfo(file : List<MultipartBody.Part>, body : HashMap<String, RequestBody>,data : HostRegisterData) {
        var token : String? = null
        Application.user!!.getIdToken(true)
            .addOnCompleteListener(object : OnCompleteListener<GetTokenResult?> {
                override fun onComplete(task: Task<GetTokenResult?>) {
                    if (task.isSuccessful()) {
                        token = task.result!!.token.toString()
                        Log.d("token2",task.result!!.token.toString())
                        CoroutineScope(Dispatchers.IO).launch {
                            PrayerRoomHostRepository().registerPlayerRoom("Bearer "+token,file,body).let {
                                if(it.isSuccessful){
                                    Log.d("token2","success1")
                                    Log.d("token2",it.body()!!.id.toString())
                                    registerPrayerRoomData("Bearer "+token,it.body()!!.id!!,data)

                                }

                                else{
                                    Log.d("token2","fail1")
                                }
                                Log.d("imageTest",it.code().toString())
                            }
                        }
                    }
                }
            })
    }

    suspend fun registerPrayerRoomData(token : String, id : String, data : HostRegisterData ){
        PrayerRoomHostRepository().registerPlayerRoomdata(token,id,data).let {
            if(it.isSuccessful){
                userHost(token)
                Log.d("token2","success2")

            }
            else{
                Log.d("token2","fail2")
            }

        }

    }
    suspend fun userHost(token : String){
        userRepository.userHost(token, UserHost(true)).let{
            if(it.isSuccessful){
                postPrayerRoom.postValue(true)
            }
        }
    }
    fun postPrayerRoomInfoTest(body : HostRegisterData) {
        var token : String? = null
        Application.user!!.getIdToken(true)
            .addOnCompleteListener(object : OnCompleteListener<GetTokenResult?> {
                override fun onComplete(task: Task<GetTokenResult?>) {
                    if (task.isSuccessful()) {
                        token = task.result!!.token.toString()
                        Log.d("token2",task.result!!.token.toString())
                        CoroutineScope(Dispatchers.IO).launch {
                            PrayerRoomHostRepository().registerPlayerRoomdataTest("Bearer "+token,body).let {
                                if(it.isSuccessful){
                                    Log.d("token2","success1")
                                    Log.d("token2",it.body()!!.id.toString())
                                    userHost("Bearer "+token)

                                }

                                else{
                                    Log.d("token2","fail1")
                                }
                                Log.d("imageTest",it.code().toString())
                            }
                        }
                    }
                }
            })
    }
}
