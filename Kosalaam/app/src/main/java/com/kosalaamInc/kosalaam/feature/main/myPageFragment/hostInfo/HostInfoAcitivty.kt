package com.kosalaamInc.kosalaam.feature.main.myPageFragment.hostInfo

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.kosalaamInc.kosalaam.R
import com.kosalaamInc.kosalaam.databinding.ActivityHostInfomationBinding
import com.kosalaamInc.kosalaam.feature.main.myPageFragment.hostInfo.hostInfoEdit.HostInfoEditAcitvity

class HostInfoAcitivty : AppCompatActivity(){

    private var binding : ActivityHostInfomationBinding? = null

    private val viewModel : HostInfoViewModel by lazy{
        ViewModelProvider(this).get(HostInfoViewModel::class.java)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView<ActivityHostInfomationBinding>(
            this, R.layout.activity_host_infomation).apply {
                lifecycleOwner = this@HostInfoAcitivty
                hostInfoVm = viewModel
        }
        initObserve()
        initClickListener()
    }

    private fun initObserve(){

    }

    private fun initClickListener(){
        binding!!.tvHostInfoEdit.setOnClickListener {
            startActivity(Intent(this,HostInfoEditAcitvity::class.java))
        }
    }


}