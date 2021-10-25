package com.kosalaamInc.kosalaam.feature.main.myPageFragment.personalInfo

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.kosalaamInc.kosalaam.R
import com.kosalaamInc.kosalaam.databinding.ActivityPersonalInfomationBinding
import com.kosalaamInc.kosalaam.feature.main.myPageFragment.personalInfo.userInfoEdit.UserInfoEditActivity
import com.kosalaamInc.kosalaam.util.CheckInternet


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
        else{
            var passwordText :String? = ""
            for(i in 0..com.kosalaamInc.kosalaam.global.Application.prefs.getString("password","").length-1){
                passwordText+= "●"
            }
            binding!!.tvHostInfoPasswordInit.text = passwordText
        }
        initObserve()
    }

    private fun initObserve(){
        with(viewModel){
            userData.observe(this@PersonalInfoActivity, Observer {
                if(it.profileImg!=null){
                    Glide.with(this@PersonalInfoActivity).load(it.profileImg).circleCrop().into(binding!!.cvHostInfoProfileImage)
                }
                // name,phoneNum plus -
                binding!!.tvHostInfoEmail.text = it.email
                binding!!.tvHostInfoEmailSetInit.text = it.email
                binding!!.tvHostInfoPhoneNumInit.text = it.phoneNumber
            })
        }

    }

    private fun initClickListener(){
        binding!!.ivHostInfoBackArrow.setOnClickListener {

        }

        binding!!.tvHostInfoEdit.setOnClickListener {
            startActivity(Intent(this,UserInfoEditActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        if (CheckInternet().checkInternet(this)) {
            viewModel.getUserInfo()
        } else {
            Toast.makeText(this, "Check your internet", Toast.LENGTH_SHORT).show()
        }

    }
}