package com.kosalaamInc.kosalaam.feature.main.myPageFragment.phoneNumRegister

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.GetTokenResult
import com.google.gson.JsonObject
import com.kosalaamInc.kosalaam.global.Application
import com.kosalaamInc.kosalaam.model.data.UserCertified
import com.kosalaamInc.kosalaam.model.data.UserData
import com.kosalaamInc.kosalaam.model.network.response.UserResponse
import com.kosalaamInc.kosalaam.repository.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONException
import org.json.JSONObject

class PhoneNumRegisterViewModel : ViewModel(){
    private val _userData = MutableLiveData<Boolean>()
    val userData : MutableLiveData<Boolean> get() = _userData

    fun putUserCertified(isCertificated : Boolean, phoneNum : String) {
        var token : String? = null
        Application.user!!.getIdToken(true)
            .addOnCompleteListener(object : OnCompleteListener<GetTokenResult?> {
                override fun onComplete(task: Task<GetTokenResult?>) {
                    if (task.isSuccessful()) {
                        token = task.result!!.token.toString()
                        Log.d("token2",task.result!!.token.toString())
                        CoroutineScope(Dispatchers.IO).launch {
                            UserRepository().putUser("Bearer "+token,UserCertified(isCertificated,phoneNum)).let {
                                if(it.isSuccessful){
                                    userData.postValue(true)
                                }
                                else{

                                }
                                Log.d("phoneNumTest",it.message().toString())
                                Log.d("phoneNumTest",it.code().toString())
                              //  Log.d("phoneNumTest",user.isCertificated.toString()+" "+user.phoneNumber.toString())
                            }
                        }
                    }
                }
            })
    }

}