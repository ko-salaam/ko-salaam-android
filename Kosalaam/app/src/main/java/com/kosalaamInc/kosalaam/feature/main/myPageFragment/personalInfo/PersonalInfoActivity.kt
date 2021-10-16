package com.kosalaamInc.kosalaam.feature.main.myPageFragment.personalInfo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.kosalaamInc.kosalaam.R
import com.kosalaamInc.kosalaam.databinding.ActivityPersonalInfomationBinding


class PersonalInfoActivity : AppCompatActivity(){
    private var binding : ActivityPersonalInfomationBinding? = null
    private  val viewModel : PersonalInfoViewModel by lazy{
        ViewModelProvider(this).get(PersonalInfoViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView<ActivityPersonalInfomationBinding>(
            this, R.layout.activity_host_info_edit).apply {
            lifecycleOwner = this@PersonalInfoActivity
            personalInfoVm = viewModel
        }
        initObserve()
    }

    private fun initObserve(){

    }
}