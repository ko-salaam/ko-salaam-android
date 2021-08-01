package com.kosalaamInc.kosalaam.feature.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kosalaamInc.kosalaam.Util.Event

class LoginViewModel : ViewModel(){
    private val _signInBt = MutableLiveData<Event<Boolean>>()
    private val _signUpBt = MutableLiveData<Event<Boolean>>()
    private val _facebookBt = MutableLiveData<Event<Boolean>>()
    private val _googleBt = MutableLiveData<Event<Boolean>>()

    val signIn_Bt: LiveData<Event<Boolean>> get() = _signInBt
    val signUp_Bt: LiveData<Event<Boolean>> get() = _signUpBt
    val facebook_Bt: LiveData<Event<Boolean>> get() = _facebookBt
    val google_Bt: LiveData<Event<Boolean>> get() = _googleBt


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