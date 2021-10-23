package com.kosalaamInc.kosalaam.feature.main.myPageFragment.phoneNumRegister

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.kosalaamInc.kosalaam.R
import com.kosalaamInc.kosalaam.databinding.ActivityHostResistrationBinding
import com.kosalaamInc.kosalaam.databinding.ActivityPhoneNumRegisterBinding


class PhoneNumRegisterActivity : AppCompatActivity() {
    private var binding : ActivityPhoneNumRegisterBinding? = null

    private val viewModel : PhoneNumRegisterViewModel by lazy{
        ViewModelProvider(this).get(PhoneNumRegisterViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView<ActivityPhoneNumRegisterBinding>(
            this, R.layout.activity_phone_num_register
        ).apply {
            lifecycleOwner = this@PhoneNumRegisterActivity
            phoneRegisterVm = viewModel
        }
    }

    private fun initClickListener(){

    }
}