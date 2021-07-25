package com.kosalaamInc.kosalaam.Feature.Login

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.kosalaamInc.kosalaam.R
import com.kosalaamInc.kosalaam.databinding.ActivityLoginInBinding
import com.kosalaamInc.kosalaam.databinding.ActivityMainBinding

class LoginInActivity : AppCompatActivity(){
    private lateinit var mBinding : ActivityLoginInBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityLoginInBinding.inflate(layoutInflater)
        setContentView(mBinding.root)


    }

}