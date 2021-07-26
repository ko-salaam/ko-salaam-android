package com.kosalaamInc.kosalaam.feature.signUp

import android.graphics.Color
import android.os.Build
import android.text.Editable
import android.util.Log
import androidx.lifecycle.*
import com.kosalaamInc.kosalaam.R
import com.kosalaamInc.kosalaam.Util.Event
import java.util.regex.Pattern


//add email textchange?
class SignUpViewModel : ViewModel() {

    companion object {
        var click: Int = 0
        var emailCheck: Boolean = false
        var verifyCheck: Boolean = false
        var passwordVisible : Boolean = false
        var passwordVisible2 : Boolean = false
        var passWordCheck : Boolean =false
        var passWordCheck2 : Boolean = false
        var passWordText : String? = null
        var passWordText2 : String? = null
    }

    private val emailValidation =
        "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"
    private val passwordValidaion =
        "^((?=.*[a-zA-Z])(?=.*[0-9])(?=.*[^a-zA-Z0-9가-힣]).{8,})$"

    private val _emailEvent = MutableLiveData<Event<String>>()
    private val _emailBtEvent = MutableLiveData<Event<Boolean>>()
    private val _emailCheckEvent = MutableLiveData<Event<String>>()
    private val _verifyCheckEvent = MutableLiveData<Event<String>>() // onTextChange
    private val _verifyCheckEventAfter = MutableLiveData<Event<String>>()
    private val _passwordEventAfter = MutableLiveData<Event<String>>()
    private val _passwordCheckEventAfter = MutableLiveData<Event<String>>()
    private val _passwordVisible = MutableLiveData<Event<Boolean>>()
    private val _passwordVisible2 = MutableLiveData<Event<Boolean>>()

    val email_BtEvent: LiveData<Event<Boolean>> get() = _emailBtEvent
    val edit_email: LiveData<Event<String>> get() = _emailEvent
    val email_check: LiveData<Event<String>> get() = _emailCheckEvent
    val verify_check_after: LiveData<Event<String>> get() = _verifyCheckEventAfter
    val password_after : LiveData<Event<String>> get() = _passwordEventAfter
    val passwordCheck_after : LiveData<Event<String>> get() = _passwordCheckEventAfter
    val passWordVisible : LiveData<Event<Boolean>> get() = _passwordVisible
    val passWordVisible2 : LiveData<Event<Boolean>> get() = _passwordVisible2

    fun onEmailClickEvent() { // NextBt
        _emailBtEvent.value = Event(true)
    }
    fun onVerifyCheckAfter(text: Editable){
        _verifyCheckEventAfter.value = Event(text.toString())
        if(text.toString() == "000000"){
            verifyCheck = true
        }
        else{
            verifyCheck = false
        }
    }

    fun onEmailCheck(text: Editable){
        var email = text.toString().trim()
        val p = Pattern.matches(emailValidation, email)
        if (p) {
            emailCheck = true
        } else {
            emailCheck = false
        }
        _emailCheckEvent.value=Event(text.toString())


    }

    fun onEmailChanged(text: CharSequence) {
        _emailEvent.value = Event(text.toString())
        var email = text.toString().trim()
        val p = Pattern.matches(emailValidation, email)
        if (p) {
            emailCheck = true
        } else {
            emailCheck = false
        }
    }

    fun onPassWordChanged(text: CharSequence){
        passWordText=text.toString()
        var password = text.toString().trim()
        val p = Pattern.matches(passwordValidaion,password)
        if(p){
            Log.d("signup","success1")
            passWordCheck = true
        }
        else{
            passWordCheck= false
        _passwordEventAfter.value = Event(text.toString())

        }
    }

    fun onPassWordCheckChanged(text: CharSequence){
        passWordText2 = text.toString()
        var password = text.toString().trim()
        val p = Pattern.matches(passwordValidaion,password)
        if(p){
            Log.d("signup","success")
            passWordCheck2 = true
        }
        else{
            passWordCheck2= false
        }
        _passwordCheckEventAfter.value = Event(text.toString())

    }

    fun onPassWordVisible(){
        _passwordVisible.value = Event(true)
    }

    fun onPassWordVisible2(){
        _passwordVisible2.value = Event(true)
    }
}

inline fun <T> LiveData<Event<T>>.eventObserve(
    owner: LifecycleOwner,
    crossinline onChanged: (T) -> Unit
): Observer<Event<T>> {
    val wrappedObserver = Observer<Event<T>> { t ->
        t.getContentIfNotHandled()?.let {
            onChanged.invoke(it)
        }
    }
    observe(owner, wrappedObserver)
    return wrappedObserver
}