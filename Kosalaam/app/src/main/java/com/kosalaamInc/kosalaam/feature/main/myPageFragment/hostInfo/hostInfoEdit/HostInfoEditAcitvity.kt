package com.kosalaamInc.kosalaam.feature.main.myPageFragment.hostInfo.hostInfoEdit

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.kosalaamInc.kosalaam.R
import com.kosalaamInc.kosalaam.databinding.ActivityHostInfoEditBinding
import com.kosalaamInc.kosalaam.databinding.ActivityHostInfomationBinding

class HostInfoEditAcitvity : AppCompatActivity(){

    private var binding : ActivityHostInfoEditBinding? = null
    private  val viewModel : HostInfoEditViewModel by lazy{
        ViewModelProvider(this).get(HostInfoEditViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView<ActivityHostInfoEditBinding>(
            this, R.layout.activity_host_info_edit).apply {
            lifecycleOwner = this@HostInfoEditAcitvity
            hostInfoEditVm = viewModel
        }
        initObserve()
    }

    private fun initObserve(){

    }
}