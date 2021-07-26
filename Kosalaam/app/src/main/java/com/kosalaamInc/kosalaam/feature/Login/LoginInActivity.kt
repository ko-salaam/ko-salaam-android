package com.kosalaamInc.kosalaam.feature.Login

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kosalaamInc.kosalaam.databinding.ActivityLoginInBinding

class LoginInActivity : AppCompatActivity(){
    private lateinit var mBinding : ActivityLoginInBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityLoginInBinding.inflate(layoutInflater)
        setContentView(mBinding.root)


    }

}