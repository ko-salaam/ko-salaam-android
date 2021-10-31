package com.kosalaamInc.kosalaam.global

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GetTokenResult
import com.kosalaamInc.kosalaam.util.PreferenceUtil
import org.conscrypt.Conscrypt
import java.security.Security

class Application : Application() {
    companion object {
        var user : FirebaseUser? = null
        var BASE_URL = "http://52.79.248.96:8080/"
        var searchKeyword : String? = null
        // lateinit
        lateinit var prefs : PreferenceUtil
    }

    override fun onCreate() {
        prefs = PreferenceUtil(applicationContext)
        super.onCreate()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        Security.insertProviderAt(Conscrypt.newProvider(), 1)
    }


    // prefs platform - email / facebook / google
    //  userEmail - password / userToken
}