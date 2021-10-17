package com.kosalaamInc.kosalaam.feature.main.myPageFragment.personalInfo

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.kosalaamInc.kosalaam.R
import com.kosalaamInc.kosalaam.databinding.ActivityPersonalInfomationBinding
import com.kosalaamInc.kosalaam.feature.main.myPageFragment.personalInfo.userInfoEdit.UserInfoEditActivity


class PersonalInfoActivity : AppCompatActivity(){
    private var binding : ActivityPersonalInfomationBinding? = null
    private  val viewModel : PersonalInfoViewModel by lazy{
        ViewModelProvider(this).get(PersonalInfoViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView<ActivityPersonalInfomationBinding>(
            this, R.layout.activity_personal_infomation).apply {
            lifecycleOwner = this@PersonalInfoActivity
            personalInfoVm = viewModel
        }
        initClickListener()
        if(com.kosalaamInc.kosalaam.global.Application.prefs.getString("platform","")!="email"){
            binding!!.clHostInfoPassword.visibility= View.GONE
            binding!!.view15HostInfo.visibility= View.GONE
            binding!!.view16HostInfo.visibility= View.GONE
            binding!!.view17HostInfo.visibility= View.GONE
        }
        initObserve()
    }

    private fun initObserve(){

    }

    private fun initClickListener(){
        binding!!.ivHostInfoBackArrow.setOnClickListener {
        }

        binding!!.tvHostInfoEdit.setOnClickListener {
            startActivity(Intent(this,UserInfoEditActivity::class.java))
        }
    }
}