package com.kosalaamInc.kosalaam.feature.signUp

import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.text.InputType
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.kosalaamInc.kosalaam.R
import com.kosalaamInc.kosalaam.databinding.ActivitySignupBinding
import com.kosalaamInc.kosalaam.feature.loginIn.LoginInActivity
import java.util.regex.Pattern


class SignUpActivity : AppCompatActivity() {

    private var binding: ActivitySignupBinding? = null
    private lateinit var wrongAnim : Animation


    private val viewModel: SignUpViewModel by lazy {
        ViewModelProvider(this).get(SignUpViewModel::class.java)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView<ActivitySignupBinding>(
            this, R.layout.activity_signup
        ).apply {
            lifecycleOwner = this@SignUpActivity
            signUpVm = viewModel
        }
        binding!!.etSignupHide2.transformationMethod =BiggerDotPasswordTransformationMethod
        binding!!.loginEditPassword.transformationMethod =BiggerDotPasswordTransformationMethod
        wrongAnim = AnimationUtils.loadAnimation(applicationContext,R.anim.signup_wrong_anim)
        initViewObserve()

    }

    private fun initViewObserve() {
        with(viewModel) {
            email_BtEvent.observe(this@SignUpActivity, Observer {
                it.getContentIfNotHandled()?.let {
                    when (SignUpViewModel.click) {
                        0 -> {
                            binding!!.tvSignupNext
                            binding!!.clSignupHide1.visibility = View.VISIBLE
                            binding!!.tvSignupNext.isClickable = false
                            binding!!.tvSignupNext.background =
                                getDrawable(R.drawable.login_defaultback)
                            binding!!.tvSignupNext.setText("Verify")
                            SignUpViewModel.click += 1
                        }
                        // add email check func
                        1 -> {
                            if (SignUpViewModel.verifyCheck == false) {
                                binding!!.tvSignupWrong.visibility = View.VISIBLE
                                binding!!.tvSignupWrong.startAnimation(wrongAnim)
                            } else {
                                binding!!.tvSignupWrong.visibility = View.GONE
                                binding!!.clSignupHide1.visibility = View.GONE
                                binding!!.tvSignupNext.isClickable = false
                                binding!!.tvSignupNext.background =
                                    getDrawable(R.drawable.login_defaultback)
                                binding!!.etLoginEditEmail.visibility = View.GONE
                                binding!!.viewLoginView1.visibility = View.GONE
                                binding!!.clSignupHide2.visibility = View.VISIBLE
                                binding!!.tvSignupNext.setText("Sign in")
                                SignUpViewModel.click += 1
                            }
                        }
                    }
                }
            })
            edit_email.observe(this@SignUpActivity, Observer {
                it.getContentIfNotHandled()?.let {
                    if (SignUpViewModel.emailCheck == true && SignUpViewModel.click == 0) {
                        binding!!.tvSignupNext.background = getDrawable(R.drawable.login_mainoval)
                        binding!!.tvSignupNext.isClickable = true
                    } else {
                        binding!!.tvSignupNext.isClickable = false
                        binding!!.tvSignupNext.background =
                            getDrawable(R.drawable.login_defaultback)

                    }
                }
            })
            email_check.observe(this@SignUpActivity, Observer {
                it.getContentIfNotHandled()?.let {
                    if (SignUpViewModel.emailCheck == true && SignUpViewModel.click == 0) {
                        binding!!.tvSignupNext.background = getDrawable(R.drawable.login_mainoval)
                        binding!!.tvSignupNext.isClickable = true
                    } else {
                        binding!!.tvSignupNext.isClickable = false
                        binding!!.tvSignupNext.background =
                            getDrawable(R.drawable.login_defaultback)

                    }
                }
            })
            verify_check_after.observe(this@SignUpActivity, Observer {
                it.getContentIfNotHandled()?.let {
                    if (it.length == 6 && SignUpViewModel.click == 1) {
                        binding!!.tvSignupNext.background = getDrawable(R.drawable.login_mainoval)
                        binding!!.tvSignupNext.isClickable = true
                    } else {
                        binding!!.tvSignupNext.isClickable = false
                        binding!!.tvSignupNext.background =
                            getDrawable(R.drawable.login_defaultback)
                    }
                }
            })
            passWordVisible.observe(this@SignUpActivity, Observer {
                it.getContentIfNotHandled()?.let {
                    if(SignUpViewModel.passwordVisible==false){
                        SignUpViewModel.passwordVisible=true
                        binding!!.loginOffeye.setImageDrawable(getDrawable(R.drawable.login_eye_on))
                        binding!!.loginEditPassword.transformationMethod = HideReturnsTransformationMethod.getInstance()
                    }
                    else{
                        SignUpViewModel.passwordVisible=false
                        binding!!.loginOffeye.setImageDrawable(getDrawable(R.drawable.login_eye_off))
                        binding!!.loginEditPassword.transformationMethod =BiggerDotPasswordTransformationMethod
                    }

                }
            })
            passWordVisible2.observe(this@SignUpActivity, Observer {
                it.getContentIfNotHandled()?.let {
                    if(SignUpViewModel.passwordVisible2==false){
                        SignUpViewModel.passwordVisible2=true
                        binding!!.ivSignupOffeyeHide2.setImageDrawable(getDrawable(R.drawable.login_eye_on))
                        binding!!.etSignupHide2.transformationMethod = HideReturnsTransformationMethod.getInstance()
                    }
                    else{
                        SignUpViewModel.passwordVisible2=false
                        binding!!.ivSignupOffeyeHide2.setImageDrawable(getDrawable(R.drawable.login_eye_off))
                        binding!!.etSignupHide2.transformationMethod =BiggerDotPasswordTransformationMethod
                    }
                }
            })
            password_after.observe(this@SignUpActivity, Observer {
                it.getContentIfNotHandled()?.let {
                    if(SignUpViewModel.passWordCheck==true && it==SignUpViewModel.passWordText2){
                        binding!!.tvSignupNext.background = getDrawable(R.drawable.login_mainoval)
                        binding!!.tvSignupNext.isClickable = true
                    }
                    else{
                        binding!!.tvSignupNext.background = getDrawable(R.drawable.login_defaultback)
                        binding!!.tvSignupNext.isClickable = false
                    }
                }
            })
            passwordCheck_after.observe(this@SignUpActivity, Observer {
                it.getContentIfNotHandled()?.let {
                    if (SignUpViewModel.passWordCheck2==true && it ==SignUpViewModel.passWordText){
                        binding!!.tvSignupNext.background = getDrawable(R.drawable.login_mainoval)
                        binding!!.tvSignupNext.isClickable = true
                    }
                    else{
                        binding!!.tvSignupNext.background = getDrawable(R.drawable.login_defaultback)
                        binding!!.tvSignupNext.isClickable = false
                    }
                }
            })
            already_BtEvent.observe(this@SignUpActivity, Observer {
                it.getContentIfNotHandled()?.let{
                    val intent = Intent(this@SignUpActivity,LoginInActivity::class.java)
                    startActivity(intent)
                    this@SignUpActivity.finish()
                }
            })
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        this.finish()
        SignUpViewModel.passwordVisible2=false
        SignUpViewModel.passwordVisible=false
        SignUpViewModel.click = 0
        SignUpViewModel.emailCheck = false
        SignUpViewModel.verifyCheck = false
        SignUpViewModel.passWordCheck = false
        SignUpViewModel.passWordCheck2 = false
        binding = null
    }

}

object BiggerDotPasswordTransformationMethod : PasswordTransformationMethod() {

    override fun getTransformation(source: CharSequence, view: View): CharSequence {
        return PasswordCharSequence(super.getTransformation(source, view))
    }

    private class PasswordCharSequence(
        val transformation: CharSequence
    ) : CharSequence by transformation {
        override fun get(index: Int): Char = if (transformation[index] == DOT) {
            BIGGER_DOT
        } else {
            transformation[index]
        }
    }

    private const val DOT = '\u2022'
    private const val BIGGER_DOT = '●'
}