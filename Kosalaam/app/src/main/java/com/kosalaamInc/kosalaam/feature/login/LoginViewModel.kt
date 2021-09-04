package com.kosalaamInc.kosalaam.feature.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.kosalaamInc.kosalaam.repository.UserRepository
import com.kosalaamInc.kosalaam.util.Event
import kotlinx.coroutines.*

class LoginViewModel : ViewModel(){
    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient

    var UserSendJob: Job? = null

    private val _signInBt = MutableLiveData<Event<Boolean>>()
    private val _signUpBt = MutableLiveData<Event<Boolean>>()
    private val _facebookBt = MutableLiveData<Event<Boolean>>()
    private val _googleBt = MutableLiveData<Event<Boolean>>()

    private val _signUpBoolean = MutableLiveData<Boolean>()

    val signIn_Bt: LiveData<Event<Boolean>> get() = _signInBt
    val signUp_Bt: LiveData<Event<Boolean>> get() = _signUpBt
    val facebook_Bt: LiveData<Event<Boolean>> get() = _facebookBt
    val google_Bt: LiveData<Event<Boolean>> get() = _googleBt
    val signUpBoolean : LiveData<Boolean> get() = _signUpBoolean

    fun signUp(token : String){
        UserSendJob = CoroutineScope(Dispatchers.IO).launch {
                UserRepository().signUp("Bearer " +token)?.let{ response ->
                    if(response.isSuccessful){
                        Log.d("CheckSignIn","Success")
                        _signUpBoolean.value=true
                    }
                    else{

                    }
                    Log.d("CheckSignIn",response.code().toString())
                    _signUpBoolean.value=false
                }
        }
    }

    fun onSignInBtEvent(){
        _signInBt.value = Event(true)
    }

    fun onSignUpBtEvent(){
        _signUpBt.value = Event(true)
    }

    fun onFacebooktBtEvent(){
        _facebookBt.value = Event(true)
    }

    fun onGoogleBtEvent(){
        _googleBt.value = Event(true)
    }


}