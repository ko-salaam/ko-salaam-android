package com.kosalaamInc.kosalaam.Feature.signUp

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.kosalaamInc.kosalaam.R
import com.kosalaamInc.kosalaam.databinding.ActivitySignupBinding


class SignUpActivity : AppCompatActivity(){

    private lateinit var binding : ActivitySignupBinding

    private val viewModel : SignUpViewModel by lazy{
        ViewModelProvider(this).get(SignUpViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView<ActivitySignupBinding>(
            this,R.layout.activity_signup).apply {
            lifecycleOwner = this@SignUpActivity
            signUpVm = viewModel
        }
        initViewObserve()

    }
    private fun initViewObserve(){
        with(viewModel){
            edit_email.observe(this@SignUpActivity){
                when(SignUpViewModel.click){
                    0 -> binding.clSignupHide1.visibility= View.VISIBLE
                }
            }
        }
    }

}