package com.kosalaamInc.kosalaam.feature.main.myPageFragment.personalInfo.userInfoEdit

import android.app.Application
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.kosalaamInc.kosalaam.R
import com.kosalaamInc.kosalaam.databinding.ActivityPersonalInfoEditBinding
import com.kosalaamInc.kosalaam.feature.main.myPageFragment.personalInfo.userInfoEdit.changePassword.ChangePasswordActivity
import com.kosalaamInc.kosalaam.feature.main.myPageFragment.personalInfo.userInfoEdit.changePassword.ChangePasswordViewModel

class UserInfoEditActivity : AppCompatActivity(){

    companion object{
        var password = ""
    }

    private var binding : ActivityPersonalInfoEditBinding? = null
    private  val viewModel : UserInfoEditViewModel by lazy{
        ViewModelProvider(this).get(UserInfoEditViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView<ActivityPersonalInfoEditBinding>(
            this, R.layout.activity_personal_info_edit).apply {
            lifecycleOwner = this@UserInfoEditActivity
            userInfoEditVm = viewModel
        }
        if(com.kosalaamInc.kosalaam.global.Application.prefs.getString("platform","")!="email"){
            binding!!.clHostInfoPassword.visibility= View.GONE
            binding!!.view15HostInfo.visibility=View.GONE
            binding!!.view16HostInfo.visibility=View.GONE
            binding!!.view17HostInfo.visibility=View.GONE
        }

        initObserve()
    }

    private fun initObserve(){
        binding!!.tvHostInfoEdit.setOnClickListener {
            com.kosalaamInc.kosalaam.global.Application.user!!.updatePassword(ChangePasswordViewModel.passwordString)
                .addOnCompleteListener { task ->
                    if(task.isSuccessful){
                        com.kosalaamInc.kosalaam.global.Application.prefs.setString("password",ChangePasswordViewModel.passwordString)
                        this.finish()
                    }
                    else{
                        Toast.makeText(this,"Password change fail",Toast.LENGTH_SHORT).show()
                    }

                }

        }

        binding!!.ivHostInfoBackArrow.setOnClickListener {
            this.finish()
        }

        binding!!.clHostInfoPassword.setOnClickListener {
            startActivity(Intent(this,ChangePasswordActivity::class.java))
        }
    }

}