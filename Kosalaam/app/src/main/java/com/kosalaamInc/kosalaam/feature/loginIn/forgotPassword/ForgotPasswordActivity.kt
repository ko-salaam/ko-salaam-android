package com.kosalaamInc.kosalaam.feature.loginIn.forgotPassword

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.kosalaamInc.kosalaam.R
import com.kosalaamInc.kosalaam.databinding.ActivityForgotPasswordBinding
import com.kosalaamInc.kosalaam.databinding.ActivityLoginBinding

import dagger.hilt.android.AndroidEntryPoint


class ForgotPasswordActivity : AppCompatActivity(){

    private var binding : ActivityForgotPasswordBinding? = null

    private val viewModel: ForgotPasswordViewModel by lazy {
        ViewModelProvider(this).get(ForgotPasswordViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView<ActivityForgotPasswordBinding>(
            this, R.layout.activity_forgot_password
        ).apply {
            lifecycleOwner = this@ForgotPasswordActivity
            forgotPasswordVm = viewModel
        }
    }
}