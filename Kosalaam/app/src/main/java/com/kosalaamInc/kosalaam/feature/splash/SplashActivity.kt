package com.kosalaamInc.kosalaam.feature.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.kosalaamInc.kosalaam.R
import com.kosalaamInc.kosalaam.feature.login.LoginActivity
import com.kosalaamInc.kosalaam.feature.main.MainActivity
import com.kosalaamInc.kosalaam.global.Application

class SplashActivity : AppCompatActivity(){
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        auth = Firebase.auth

        if(Application.prefs.getString("platform","")=="facebook"){

            val credential = FacebookAuthProvider.getCredential(Application.prefs.getString("token",""))
            auth.signInWithCredential(credential)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Application.user =auth.currentUser
                        goMain()
                    } else {
                        goLogin()
                    }
                }

        }
        else if(Application.prefs.getString("platform","")=="google"){
            val credential = GoogleAuthProvider.getCredential(Application.prefs.getString("token",""), null)
            auth.signInWithCredential(credential)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Log.d("SplashAcitity","facebooksucess")
                        Application.user =auth.currentUser
                        goMain()
                    } else {
                        Log.d("SplashAcitity","facebookfail")
                        goLogin()
                    }
                }

        }
        else if(Application.prefs.getString("platform","")=="email"){
            auth.createUserWithEmailAndPassword(Application.prefs.getString("userEmail",""), Application.prefs.getString("password",""))
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Application.user =auth.currentUser
                        goMain()
                        // Sign in success, update UI with the signed-in user's information
                    } else {
                        goLogin()
                    }
                }

        }
        else{
            Log.d("SplashAcitity","else")
            Log.d("splashInfo_platform","this"+Application.prefs.getString("platform",""))
            Log.d("splashInfo_platform","this"+Application.prefs.getString("token",""))
            goLogin()
        }

    }
    fun goMain(){
        Handler(Looper.getMainLooper()).postDelayed(Runnable {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, 1000)


    }
    fun goLogin(){
        Handler(Looper.getMainLooper()).postDelayed(Runnable {
        startActivity(Intent(this,LoginActivity::class.java))
            finish()
        }, 1000)

    }
}