package com.kosalaamInc.kosalaam.feature.Login

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kosalaamInc.kosalaam.feature.signUp.SignUpActivity
import com.kosalaamInc.kosalaam.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity(){
    private var mBinding : ActivityLoginBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(mBinding!!.root)
        signInBtInit()
        signUpBtInit()

    }
    private fun signInBtInit(){
        mBinding!!.loginSignIn.setOnClickListener {
            val intent = Intent(this,LoginInActivity::class.java)
            startActivity(intent)
        }
    }

    private fun signUpBtInit(){
        mBinding!!.loginSignUp.setOnClickListener {
            val intent = Intent(this,SignUpActivity::class.java)
            startActivity(intent)
        }
    }

}