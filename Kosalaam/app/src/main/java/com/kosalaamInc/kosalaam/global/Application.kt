package com.kosalaamInc.kosalaam.global

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import com.google.firebase.auth.FirebaseUser
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
        Security.insertProviderAt(Conscrypt.newProvider(), 1);
    }
    // prefs platform - email / facebook / google
    //  userEmail - password / userToken


}