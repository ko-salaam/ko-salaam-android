package com.kosalaamInc.kosalaam.feature.main.myPageFragment.personalInfo.userInfoEdit

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.kosalaamInc.kosalaam.R
import com.kosalaamInc.kosalaam.databinding.ActivityPersonalInfoEditBinding

class UserInfoEditActivity : AppCompatActivity(){

    private var binding : ActivityPersonalInfoEditBinding? = null
    private  val viewModel : UserInfoEditViewModel by lazy{
        ViewModelProvider(this).get(UserInfoEditViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView<ActivityPersonalInfoEditBinding>(
            this, R.layout.activity_host_info_edit).apply {
            lifecycleOwner = this@UserInfoEditActivity
            userInfoEditVm = viewModel
        }
        initObserve()
    }

    private fun initObserve(){

    }

}