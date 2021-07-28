package com.kosalaamInc.kosalaam.feature.loginIn

import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.kosalaamInc.kosalaam.R
import com.kosalaamInc.kosalaam.databinding.ActivityLoginInBinding
import com.kosalaamInc.kosalaam.databinding.ActivitySignupBinding
import com.kosalaamInc.kosalaam.feature.Login.LoginViewModel
import com.kosalaamInc.kosalaam.feature.signUp.BiggerDotPasswordTransformationMethod
import com.kosalaamInc.kosalaam.feature.signUp.SignUpViewModel

class LoginInActivity : AppCompatActivity(){
    private var binding : ActivityLoginInBinding? = null

    private val viewModel :LoginInViewModel by lazy{
        ViewModelProvider(this).get(LoginInViewModel::class.java)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView<ActivityLoginInBinding>(
            this, R.layout.activity_login_in
        ).apply {
            lifecycleOwner = this@LoginInActivity
            loginInVm = viewModel
        }
        binding!!.loginEditPassword.transformationMethod = BiggerDotPasswordTransformationMethod
        initobserve()
    }

    private fun initobserve(){
        with(viewModel){
            email_text_after.observe(this@LoginInActivity, Observer {
                it.getContentIfNotHandled()?.let {
                    if(LoginInViewModel.emailCheck==true && LoginInViewModel.passWordCheck==true){
                        binding!!.tvLoginLoginbt.background = getDrawable(R.drawable.login_mainoval)
                        binding!!.tvLoginLoginbt.isClickable=true
                    }
                    else{
                        binding!!.tvLoginLoginbt.background = getDrawable(R.drawable.login_defaultback)
                        binding!!.tvLoginLoginbt.isClickable=false
                    }
                }

            })
            password_after.observe(this@LoginInActivity, Observer {
                it.getContentIfNotHandled()?.let {
                    if(LoginInViewModel.emailCheck==true && LoginInViewModel.passWordCheck==true){
                        binding!!.tvLoginLoginbt.background = getDrawable(R.drawable.login_mainoval)
                        binding!!.tvLoginLoginbt.isClickable=true
                    }
                    else{
                        binding!!.tvLoginLoginbt.background = getDrawable(R.drawable.login_defaultback)
                        binding!!.tvLoginLoginbt.isClickable=false
                    }
                }
            })
            passWordVisible.observe(this@LoginInActivity, Observer {
                it.getContentIfNotHandled()?.let {
                    if(LoginInViewModel.passwordVisible==false){
                        LoginInViewModel.passwordVisible=true
                        binding!!.loginOffeye.setImageDrawable(getDrawable(R.drawable.login_eye_on))
                        binding!!.loginEditPassword.transformationMethod = HideReturnsTransformationMethod.getInstance()
                    }
                    else{
                        LoginInViewModel.passwordVisible=false
                        binding!!.loginOffeye.setImageDrawable(getDrawable(R.drawable.login_eye_off))
                        binding!!.loginEditPassword.transformationMethod =BiggerDotPasswordTransformationMethod
                    }
                }
            })
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        LoginInViewModel.emailCheck=false
        LoginInViewModel.passWordCheck=false
        LoginInViewModel.passwordVisible=false
        binding=null
    }

}