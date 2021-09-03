package com.kosalaamInc.kosalaam.global

import android.app.Application
import org.conscrypt.Conscrypt
import java.security.Security

class Application : Application() {
    companion object {
        var BASE_URL = "http://52.79.248.96:8080/"

        // lateinit
        var prefs = ""
    }

    override fun onCreate() {
        super.onCreate()
        Security.insertProviderAt(Conscrypt.newProvider(), 1);
    }

}