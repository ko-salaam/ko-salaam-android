package com.kosalaamInc.kosalaam.feature.main.myPageFragment

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.GetTokenResult
import com.kosalaamInc.kosalaam.global.Application
import com.kosalaamInc.kosalaam.model.network.response.UserResponse
import com.kosalaamInc.kosalaam.repository.SearchRepository
import com.kosalaamInc.kosalaam.repository.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MyPageViewModel : ViewModel(){
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

}