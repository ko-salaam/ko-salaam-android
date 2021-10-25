package com.kosalaamInc.kosalaam.feature.main.myPageFragment.personalInfo.userInfoEdit

import android.app.Application
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
import com.kosalaamInc.kosalaam.databinding.ActivityPersonalInfoEditBinding
import com.kosalaamInc.kosalaam.feature.main.myPageFragment.personalInfo.userInfoEdit.changePassword.ChangePasswordActivity
import com.kosalaamInc.kosalaam.feature.main.myPageFragment.personalInfo.userInfoEdit.changePassword.ChangePasswordViewModel
import com.kosalaamInc.kosalaam.feature.main.myPageFragment.phoneNumRegister.PhoneNumRegisterActivity

class UserInfoEditActivity : AppCompatActivity() {

    companion object {
        var password = ""
    }

    private var binding: ActivityPersonalInfoEditBinding? = null
    private val viewModel: UserInfoEditViewModel by lazy {
        ViewModelProvider(this).get(UserInfoEditViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView<ActivityPersonalInfoEditBinding>(
            this, R.layout.activity_personal_info_edit).apply {
            lifecycleOwner = this@UserInfoEditActivity
            userInfoEditVm = viewModel
        }
        password = com.kosalaamInc.kosalaam.global.Application.prefs.getString("password","") // password change verify 후?
        if (com.kosalaamInc.kosalaam.global.Application.prefs.getString("platform",
                "") != "email"
        ) {
            binding!!.clHostInfoPassword.visibility = View.GONE
            binding!!.view15HostInfo.visibility = View.GONE
            binding!!.view16HostInfo.visibility = View.GONE
            binding!!.view17HostInfo.visibility = View.GONE
        }
        initClickListener()
        initObserve()
    }

    private fun initObserve() {
        with(viewModel) {
            userData.observe(this@UserInfoEditActivity, Observer {
                if (it.profileImg != null) {
                    Glide.with(this@UserInfoEditActivity).load(it.profileImg).circleCrop()
                        .into(binding!!.cvHostInfoProfileImage)
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
            this.finish()
        }

        binding!!.clHostInfoPassword.setOnClickListener {
            startActivity(Intent(this, ChangePasswordActivity::class.java))
        }
        binding!!.tvHostInfoEdit.setOnClickListener {
            if(com.kosalaamInc.kosalaam.global.Application.prefs.getString("platform","")=="email"){
                com.kosalaamInc.kosalaam.global.Application.user!!.updatePassword(
                    password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            com.kosalaamInc.kosalaam.global.Application.prefs.setString("password",
                                password)
                        } else {
                            Toast.makeText(this, "Password change fail", Toast.LENGTH_SHORT).show()
                        }
                    }
            }
            this.finish()
        }
        binding!!.clHostInfoPhoneNum.setOnClickListener {
            startActivity(Intent(this,PhoneNumRegisterActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getUserInfo()
    }
}