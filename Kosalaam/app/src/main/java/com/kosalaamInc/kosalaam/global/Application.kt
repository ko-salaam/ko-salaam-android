package com.kosalaamInc.kosalaam.global

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import org.conscrypt.Conscrypt
import java.security.Security

class Application : Application() {
    companion object {
        var BASE_URL = "http://52.79.248.96:8080/"
        var searchKeyword : String? = null
        // lateinit
        var prefs = ""
    }

    override fun onCreate() {
        super.onCreate()
        Security.insertProviderAt(Conscrypt.newProvider(), 1);
    }


}