package com.kosalaamInc.kosalaam.Feature.signUp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kosalaamInc.kosalaam.Util.Event

class SignUpViewModel : ViewModel() {
    companion object{
        val click : Int =0
    }
    private val _emailEvent = MutableLiveData<Event<String>>()
    private val _emailBtEvent = MutableLiveData<Event<Boolean>>()
    val emailEvent: LiveData<Event<Boolean>> get() = _emailBtEvent


    val edit_email : MutableLiveData<String> = MutableLiveData()

    fun onEmailClickEvent() {
        _emailBtEvent.value = Event(true)
    }

//    fun onClickEvent1(text: String) {
//
//        _emailEvent.value = Event(text)
//    }
}