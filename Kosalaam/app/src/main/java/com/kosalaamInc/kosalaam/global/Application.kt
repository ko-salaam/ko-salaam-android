package com.kosalaamInc.kosalaam.global

import android.app.Application

class Application : Application() {
    companion object {
        var BASE_URL = "https://dev-kosalaam.herokuapp.com"

        // lateinit
        var prefs = ""
    }

    override fun onCreate() {
        super.onCreate()
    }

}