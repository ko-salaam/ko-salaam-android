package com.kosalaamInc.kosalaam.feature.main.myPageFragment.personalInfo.userInfoEdit.changePassword

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kosalaamInc.kosalaam.feature.signUp.SignUpViewModel
import com.kosalaamInc.kosalaam.util.Event
import java.util.regex.Pattern

class ChangePasswordViewModel : ViewModel(){

    companion object{
        var passWordCheck1 : Boolean = false
        var passWordCheck2 : Boolean = false
        var passWordCheck3 : Boolean = false
        var passwordString = ""
    }




    private val passwordValidaion =
        "^((?=.*[a-zA-Z])(?=.*[0-9])(?=.*[^a-zA-Z0-9가-힣]).{8,})$"

    private val _passwordEventAfter1 = MutableLiveData<Event<String>>()
    val password_after1 : LiveData<Event<String>> get() = _passwordEventAfter1
    private val _passwordEventAfter2 = MutableLiveData<Event<String>>()
    val password_after2 : LiveData<Event<String>> get() = _passwordEventAfter2
    private val _passwordEventAfter3 = MutableLiveData<Event<String>>()
    val password_after3 : LiveData<Event<String>> get() = _passwordEventAfter3

    fun onCurrentPassWordChanged(text: CharSequence){
        var password = text.toString().trim()
        val p = Pattern.matches(passwordValidaion,password)
        if(p){
            passWordCheck1 = true
        }
        else{
            passWordCheck1 = false
        }
        _passwordEventAfter1.value = Event(text.toString())
    }

    fun onPassWordChanged(text: CharSequence){
        var password = text.toString().trim()
        val p = Pattern.matches(passwordValidaion,password)
        if(p){
            passWordCheck2 = true
        }
        else{
            passWordCheck2 = false
        }
        _passwordEventAfter2.value = Event(text.toString())
    }

    fun onPassWordChangedCheck(text: CharSequence){
        var password = text.toString().trim()
        passwordString=password
        val p = Pattern.matches(passwordValidaion,password)
        if(p){
            passWordCheck3 = true
        }
        else{
            passWordCheck3 = false
        }
        _passwordEventAfter3.value = Event(text.toString())

    }

}