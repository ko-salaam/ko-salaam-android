package com.kosalaamInc.kosalaam.feature.main.myPageFragment

import android.app.Dialog
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.kosalaamInc.kosalaam.R
import com.kosalaamInc.kosalaam.databinding.FragmentMypageBinding
import com.kosalaamInc.kosalaam.feature.login.LoginActivity
import com.kosalaamInc.kosalaam.feature.main.myPageFragment.getHelp.GetHelpActivity
import com.kosalaamInc.kosalaam.feature.main.myPageFragment.hostInfo.HostInfoAcitivty
import com.kosalaamInc.kosalaam.feature.main.myPageFragment.hostResistration.HostResistrationActivity
import com.kosalaamInc.kosalaam.feature.main.myPageFragment.learnAbout.LearnAboutActivity
import com.kosalaamInc.kosalaam.feature.main.myPageFragment.personalInfo.PersonalInfoActivity
import com.kosalaamInc.kosalaam.feature.main.myPageFragment.phoneNumRegister.PhoneNumRegisterActivity
import com.kosalaamInc.kosalaam.feature.main.myPageFragment.privacyPolicy.PrivacyPolicyActivity
import com.kosalaamInc.kosalaam.global.Application
import com.kosalaamInc.kosalaam.util.CheckInternet


class MyPageFragment : Fragment() {
    private var binding: FragmentMypageBinding? = null
    private lateinit var passwordDialog: Dialog

    companion object {
        const val Tag = "MyPageFragment"
    }

    private lateinit var viewModel: MyPageViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = androidx.databinding.DataBindingUtil.inflate(inflater,
            R.layout.fragment_mypage,
            container,
            false)
        passwordDialog = Dialog(requireContext())
        passwordDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        passwordDialog.setContentView(R.layout.dialog_phonenum)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(MyPageViewModel::class.java)
        binding!!.lifecycleOwner = viewLifecycleOwner
        binding!!.myPageVm = viewModel
        initClickListener()
        initObserve()
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }

    private fun initObserve() {
        with(viewModel) {
            userData.observe(viewLifecycleOwner, Observer {

                binding!!.tvMypageEmail.text = it.email
                if (it.profileImg != null) {
                    Glide.with(this@MyPageFragment).load(it.profileImg).circleCrop()
                        .into(binding!!.cvMypageProfileImage)
                }
                // check name if(it.)
                if (it.isHost) {
                    binding!!.tvMypageHostRegistration.setOnClickListener {
                        Toast.makeText(requireContext(),
                            "Already register prayer room!",
                            Toast.LENGTH_LONG).show()
                    }
                    binding!!.tvMypageHostingInfomation.setOnClickListener {
                        startActivity(Intent(requireContext(), HostInfoAcitivty::class.java))
                    }
                } else {
                    if (it.isCertificated) {
                        binding!!.tvMypageHostRegistration.setOnClickListener {
                            startActivity(Intent(requireContext(),
                                HostResistrationActivity::class.java))
                        }
                    } else {
                        binding!!.tvMypageHostRegistration.setOnClickListener {
                            //showDialog()
                            Toast.makeText(requireContext(),
                                "Sorry we're preparing!!",
                                Toast.LENGTH_LONG).show()
                        }
                    }
                    binding!!.tvMypageHostingInfomation.setOnClickListener {
                        Toast.makeText(requireContext(),
                            "You should register prayer room first!",
                            Toast.LENGTH_LONG).show()
                    }
                }

            })
        }
    }

    private fun initClickListener() {
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
        binding!!.tvPersonalInfomation.setOnClickListener {
            startActivity(Intent(requireContext(), PersonalInfoActivity::class.java))
        }
        binding!!.tvMypageLearnAbout.setOnClickListener {
            startActivity(Intent(requireContext(), LearnAboutActivity::class.java))
        }

        binding!!.tvMypageGetHelp.setOnClickListener {
            startActivity(Intent(requireContext(), GetHelpActivity::class.java))
        }
    }

    private fun logout() {
        if (com.kosalaamInc.kosalaam.global.Application.prefs.getString("platform",
                "") == "facebook" ||
            com.kosalaamInc.kosalaam.global.Application.prefs.getString("platform", "") == "google"
        ) {
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

    private fun contactUs() {
        try {
            val email = Intent(Intent.ACTION_SEND)
            email.type = "text/plain"
            email.putExtra(Intent.EXTRA_EMAIL, arrayOf("kosalaamapp@gmail.com"))
            startActivity(email)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(requireContext(), "Check email application", Toast.LENGTH_SHORT).show()
        }
    }

    private fun showDialog() {
        passwordDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        passwordDialog.show()
        val cancel: ImageView = passwordDialog.findViewById(R.id.dialog_image)
        val registerNow: TextView = passwordDialog.findViewById(R.id.dialog_tv4)
        val notNow: TextView = passwordDialog.findViewById(R.id.dialog_tv5)

        cancel.setOnClickListener {
            passwordDialog.dismiss()
        }

        notNow.setOnClickListener {
            passwordDialog.dismiss()
        }

        registerNow.setOnClickListener {
            startActivity(Intent(requireContext(), PhoneNumRegisterActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        if (CheckInternet().checkInternet(requireActivity())) {
            viewModel.getUserInfo()
        } else {
            Toast.makeText(requireContext(), "Check your internet", Toast.LENGTH_SHORT).show()
        }

    }
}