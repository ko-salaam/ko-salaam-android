package com.kosalaamInc.kosalaam.feature.main.myPageFragment.personalInfo.userInfoEdit.changePassword

import android.app.Application
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.kosalaamInc.kosalaam.R
import com.kosalaamInc.kosalaam.databinding.ActivityChangePasswordBinding
import com.kosalaamInc.kosalaam.feature.main.myPageFragment.personalInfo.userInfoEdit.UserInfoEditActivity
import com.kosalaamInc.kosalaam.feature.signUp.BiggerDotPasswordTransformationMethod

class ChangePasswordActivity : AppCompatActivity(){
    private var binding : ActivityChangePasswordBinding? = null
    private  val viewModel : ChangePasswordViewModel by lazy{
        ViewModelProvider(this).get(ChangePasswordViewModel::class.java)
    }
    private var status = 1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView<ActivityChangePasswordBinding>(
            this, R.layout.activity_change_password).apply {
            lifecycleOwner = this@ChangePasswordActivity
            changePasswordVm = viewModel
        }
        initObserve()
        initClickListener()
        binding!!.etChangePasswordCurrent.transformationMethod=
            BiggerDotPasswordTransformationMethod
        binding!!.etChangePasswordNewpassword.transformationMethod=
            BiggerDotPasswordTransformationMethod
        binding!!.etChangePasswordNewpasswordCheck.transformationMethod=
            BiggerDotPasswordTransformationMethod

    }
    private fun initObserve(){
        with(viewModel){
            password_after1.observe(this@ChangePasswordActivity, Observer {
                it.getContentIfNotHandled()?.let {
                    if(it==com.kosalaamInc.kosalaam.global.Application.prefs.getString("password","")){
                        binding!!.tvChangePasswordVerify.isClickable=true
                        binding!!.tvChangePasswordVerify.background = getDrawable(R.drawable.login_mainoval)
                    }
                    else{
                        binding!!.tvChangePasswordVerify.isClickable=false
                        binding!!.tvChangePasswordVerify.background = getDrawable(R.drawable.login_defaultback)
                    }
                }
            })
            password_after2.observe(this@ChangePasswordActivity, Observer {
                it.getContentIfNotHandled()?.let {
                    if(ChangePasswordViewModel.passWordCheck2==true && it==binding!!.etChangePasswordNewpasswordCheck.text.toString()){
                        binding!!.tvChangePasswordVerify.isClickable=true
                        binding!!.tvChangePasswordVerify.background = getDrawable(R.drawable.login_mainoval)
                    }
                    else{
                        binding!!.tvChangePasswordVerify.isClickable=false
                        binding!!.tvChangePasswordVerify.background = getDrawable(R.drawable.login_defaultback)
                    }
                }
            })
            password_after3.observe(this@ChangePasswordActivity, Observer {
                it.getContentIfNotHandled()?.let {
                    if(ChangePasswordViewModel.passWordCheck3==true && it==binding!!.etChangePasswordNewpassword.text.toString()){
                        binding!!.tvChangePasswordVerify.isClickable=true
                        binding!!.tvChangePasswordVerify.background = getDrawable(R.drawable.login_mainoval)
                    }
                    else{
                        binding!!.tvChangePasswordVerify.isClickable=false
                        binding!!.tvChangePasswordVerify.background = getDrawable(R.drawable.login_defaultback)
                    }

                }
            })
        }
    }
    private fun initClickListener(){
        binding!!.ivChangePasswordBackArrow.setOnClickListener {
            this@ChangePasswordActivity.finish()
        }

        binding!!.tvChangePasswordVerify.setOnClickListener {
            if(status==1){
                status++
                binding!!.view6ChangePassword.visibility=View.VISIBLE
                binding!!.view7ChangePassword.visibility=View.GONE
                binding!!.tvChangePasswordVerify.isClickable=false
                binding!!.tvChangePasswordVerify.background = getDrawable(R.drawable.login_defaultback)
                binding!!.etChangePasswordCurrent.visibility=View.GONE
                binding!!.etChangePasswordNewpasswordCheck.visibility=View.VISIBLE
                binding!!.etChangePasswordNewpassword.visibility=View.VISIBLE
                binding!!.view5ChangePassword.visibility=View.VISIBLE
                binding!!.tvChangePasswordVerifypassword.text=getString(R.string.changePassword_enter_new_pass)
                binding!!.tvChangePasswordVerify.text=getString(R.string.changePassword_update_password)
            }
            else if(status==2){
                UserInfoEditActivity.password=ChangePasswordViewModel.passwordString
                this@ChangePasswordActivity.finish()
                //pref change
                //send to companion
                //finish
            }

        }
    }

    override fun finish() {
        super.finish()
        status=1
    }
}