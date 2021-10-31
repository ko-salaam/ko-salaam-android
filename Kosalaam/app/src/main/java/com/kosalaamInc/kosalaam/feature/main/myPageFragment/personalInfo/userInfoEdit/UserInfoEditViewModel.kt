package com.kosalaamInc.kosalaam.feature.main.myPageFragment.personalInfo.userInfoEdit

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.GetTokenResult
import com.kosalaamInc.kosalaam.global.Application
import com.kosalaamInc.kosalaam.model.data.UserData
import com.kosalaamInc.kosalaam.model.network.response.UserResponse
import com.kosalaamInc.kosalaam.repository.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody

class UserInfoEditViewModel : ViewModel(){
    private val _userData = MutableLiveData<UserResponse>()
    val userData : MutableLiveData<UserResponse> get() = _userData

    fun getUserInfo() {
        var token : String? = null
        Application.user!!.getIdToken(true)
            .addOnCompleteListener(object : OnCompleteListener<GetTokenResult?> {
                override fun onComplete(task: Task<GetTokenResult?>) {
                    if (task.isSuccessful()) {
                        token = task.result!!.token.toString()
                        Log.d("token2",task.result!!.token.toString())
                        CoroutineScope(Dispatchers.IO).launch {
                            UserRepository().authMe("Bearer "+token).let {
                                if(it.isSuccessful){
                                    userData.postValue(it.body())
                                }

                                else{

                                }
                            }
                        }
                    }
                }
            })
    }

    private val _updateUser = MutableLiveData<Boolean>()
    val updateUser : MutableLiveData<Boolean> get() = _updateUser

    fun updateUserInfo(body : UserData) {
        var token : String? = null
        Application.user!!.getIdToken(true)
            .addOnCompleteListener(object : OnCompleteListener<GetTokenResult?> {
                override fun onComplete(task: Task<GetTokenResult?>) {
                    if (task.isSuccessful()) {
                        token = task.result!!.token.toString()
                        Log.d("token2",task.result!!.token.toString())
                        CoroutineScope(Dispatchers.IO).launch {
                            UserRepository().editUser("Bearer "+token,body).let {
                                if(it.isSuccessful){
                                    updateUser.postValue(true)
                                }

                                else{

                                }
                                Log.d("imageTest",it.code().toString())
                            }
                        }
                    }
                }
            })
    }
}