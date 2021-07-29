package com.kosalaamInc.kosalaam.feature.loginIn

import android.text.Editable
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kosalaamInc.kosalaam.Util.Event
import com.kosalaamInc.kosalaam.feature.signUp.SignUpViewModel
import java.util.regex.Pattern

class LoginInViewModel : ViewModel(){

    companion object{
        var emailCheck = false
        var passWordCheck = false
        var passwordVisible : Boolean = false
    }

    private val emailValidation =
        "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"
    private val passwordValidaion =
        "^((?=.*[a-zA-Z])(?=.*[0-9])(?=.*[^a-zA-Z0-9가-힣]).{8,})$"

    private val _emailTextAfter = MutableLiveData<Event<String>>()
    private val _passwordEventAfter = MutableLiveData<Event<String>>()
    private val _passwordVisible = MutableLiveData<Event<Boolean>>()




    val email_text_after: LiveData<Event<String>> get() = _emailTextAfter
    val password_after : LiveData<Event<String>> get() = _passwordEventAfter
    val passWordVisible : LiveData<Event<Boolean>> get() = _passwordVisible

    fun onEmailChanged(text: Editable) {

        var email = text.toString().trim()
        val p = Pattern.matches(emailValidation, email)
        if (p) {
           emailCheck = true
        } else {
            emailCheck = false
        }
        _emailTextAfter.value = Event(text.toString())
    }
    // aftertextchange 확인
    fun onPassWordChanged(text: Editable){
        var password = text.toString().trim()
        val p = Pattern.matches(passwordValidaion,password)
        if(p){
            passWordCheck = true
        }
        else{
            passWordCheck = false
            _passwordEventAfter.value = Event(text.toString())

        }
        _passwordEventAfter.value =Event(text.toString())
    }
    fun onPassWordVisible(){
        _passwordVisible.value = Event(true)
    }
}