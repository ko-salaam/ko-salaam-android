package com.kosalaamInc.kosalaam.feature.main.myPageFragment

import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.kosalaamInc.kosalaam.R
import com.kosalaamInc.kosalaam.databinding.FragmentMypageBinding
import com.kosalaamInc.kosalaam.feature.login.LoginActivity
import com.kosalaamInc.kosalaam.feature.main.myPageFragment.hostInfo.HostInfoAcitivty
import com.kosalaamInc.kosalaam.feature.main.myPageFragment.privacyPolicy.PrivacyPolicyActivity
import com.kosalaamInc.kosalaam.global.Application


class MyPageFragment : Fragment(){
    private var binding : FragmentMypageBinding? = null
    companion object{
        const val Tag = "MyPageFragment"
    }
    private lateinit var viewModel : MyPageViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = androidx.databinding.DataBindingUtil.inflate(inflater,
            R.layout.fragment_mypage,
            container,
            false)
        initClickListener()
        return binding?.root

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(MyPageViewModel::class.java)
        binding!!.lifecycleOwner = viewLifecycleOwner
        binding!!.myPageVm = viewModel

    }

    override fun onDestroyView() {
        binding= null
        super.onDestroyView()
    }

    private fun initClickListener(){
        binding!!.tvMypageHostingInfomation.setOnClickListener {
            startActivity(Intent(requireContext(), HostInfoAcitivty::class.java))
        }
        binding!!.tvMypagePrivacyPolicy.setOnClickListener {
            startActivity(Intent(requireContext(), PrivacyPolicyActivity::class.java))
        }
        binding!!.tvMypageLogout.setOnClickListener {
            logout()
        }
        binding!!.tvMypageContactUs.setOnClickListener {
            contactUs()
        }
    }

    private fun logout(){
        if(com.kosalaamInc.kosalaam.global.Application.prefs.getString("platform", "")=="facebook" ||
            com.kosalaamInc.kosalaam.global.Application.prefs.getString("platform", "")=="google") {
            Firebase.auth.signOut()
        }
        com.kosalaamInc.kosalaam.global.Application.prefs.setString("platform", "")
        com.kosalaamInc.kosalaam.global.Application.prefs.setString("token", "")
        com.kosalaamInc.kosalaam.global.Application.prefs.setString("email", "")
        com.kosalaamInc.kosalaam.global.Application.prefs.setString("password", "")

        var i = Intent(requireContext(), LoginActivity::class.java)
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
        startActivity(i)
    }

    private fun contactUs(){
        try{
            val email = Intent(Intent.ACTION_SEND)
            email.type="text/plain"
            email.putExtra(Intent.EXTRA_EMAIL, arrayOf("kosalaamapp@gmail.com"))
//        email.putExtra(Intent.EXTRA_SUBJECT,
//            "<" + getString(R.string.app_name) + " " + getString(R.string.report) + ">")
//        email.putExtra(Intent.EXTRA_TEXT, """
//     앱 버전 (AppVersion):${appVersion.toString()}
//     기기명 (Device):
//     안드로이드 OS (Android OS):
//     내용 (Content):
//
//     """.trimIndent())
            email.type = "message/rfc822"
            startActivity(email)
        }
        catch (e: ActivityNotFoundException){
            Toast.makeText(context,"Check email application",Toast.LENGTH_SHORT).show()
        }

    }
}