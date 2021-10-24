package com.kosalaamInc.kosalaam.feature.main.myPageFragment.hostResistration

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.kosalaamInc.kosalaam.R
import com.kosalaamInc.kosalaam.databinding.ActivityHostResistrationBinding
import com.kosalaamInc.kosalaam.databinding.ActivityLoginInBinding
import com.kosalaamInc.kosalaam.feature.loginIn.LoginInViewModel

class HostResistrationActivity : AppCompatActivity(){
    private var binding : ActivityHostResistrationBinding? = null
    private val viewModel : HostResistrationViewModel by lazy{
        ViewModelProvider(this).get(HostResistrationViewModel::class.java)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView<ActivityHostResistrationBinding>(
            this, R.layout.activity_host_resistration
        ).apply {
            lifecycleOwner = this@HostResistrationActivity
            hostResistrationVm = viewModel
        }
    }
}