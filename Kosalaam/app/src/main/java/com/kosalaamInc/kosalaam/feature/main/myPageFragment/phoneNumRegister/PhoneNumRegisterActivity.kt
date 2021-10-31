package com.kosalaamInc.kosalaam.feature.main.myPageFragment.phoneNumRegister

import android.app.Application
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.telephony.PhoneNumberUtils
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.kosalaamInc.kosalaam.R
import com.kosalaamInc.kosalaam.databinding.ActivityPhoneNumRegisterBinding
import com.kosalaamInc.kosalaam.feature.main.myPageFragment.hostResistration.HostResistrationActivity
import com.kosalaamInc.kosalaam.model.data.UserCertified
import org.json.JSONArray
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList


class PhoneNumRegisterActivity : AppCompatActivity() {

    // code 전송시 phone num disable
    private lateinit var auth: FirebaseAuth
    private var country = ArrayList<String>()
    private var code  = ArrayList<String>()
    private var resendToken: PhoneAuthProvider.ForceResendingToken? = null
    private var storedVerificationId = ""
    private var TAG = "PhoneRegisterTest"

    companion object{
        var status : Int = 1 // 1 : mypage 2 : register
    }

    private val callbacks by lazy {
        object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            // 번호인증 혹은 기타 다른 인증(구글로그인, 이메일로그인 등) 끝난 상태
            override fun onVerificationCompleted(credential: PhoneAuthCredential) {

                // This callback will be invoked in two situations:
                // 1 - Instant verification. In some cases the phone number can be instantly
                //     verified without needing to send or enter a verification code.
                // 2 - Auto-retrieval. On some devices Google Play services can automatically
                //     detect the incoming verification SMS and perform verification without
                //     user action.
               // verifyPhoneNumberWithCode(credential)
                Handler(Looper.getMainLooper()).postDelayed({
                    verifyPhoneNumberWithCode(credential)
                }, 1000)
            }

            // 번호인증 실패 상태
            override fun onVerificationFailed(e: FirebaseException) {
                Toast.makeText(this@PhoneNumRegisterActivity,"check your phone number",Toast.LENGTH_SHORT).show()
                // This callback is invoked in an invalid request for verification is made,
                // for instance if the the phone number format is not valid.
                Log.w(TAG, "onVerificationFailed", e)
                if (e is FirebaseAuthInvalidCredentialsException) {
                    // Invalid request
                } else if (e is FirebaseTooManyRequestsException) {
                    // The SMS quota for the project has been exceeded
                }
            }

            // 전화번호는 확인 되었으나 인증코드를 입력해야 하는 상태
            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken
            ) {
                // The SMS verification code has been sent to the provided phone number, we
                // now need to ask the user to enter the code and then construct a credential
                // by combining the code with a verification ID.
                Log.d(TAG, "onCodeSent:$verificationId")
                // Save verification ID and resending token so we can use them later
                binding!!.etPhoneRegisterPhoneNum.isEnabled=false
                storedVerificationId = verificationId // verificationId 와 전화번호인증코드 매칭해서 인증하는데 사용예정
                resendToken = token
            }
        }
    }
    private fun startPhoneNumberVerification(phoneNumber: String) {
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(phoneNumber)       // Phone number to verify
            .setTimeout(90L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(this)                 // Activity (for callback binding)
            .setCallbacks(callbacks)          // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)

    }
    private fun verifyPhoneNumberWithCode(phoneAuthCredential: PhoneAuthCredential) {
//        UserInfo.tel = binding.phoneAuthEtPhoneNum.text.toString()
//        if (UserInfo.tel.isNotBlank() && UserInfo.phoneAuthNum.isNotBlank() &&
//            (UserInfo.tel == binding.phoneAuthEtPhoneNum.text.toString() && UserInfo.phoneAuthNum == binding.phoneAuthEtAuthNum.text.toString())
//        ) { // 이전에  인증한 번호와 인증번호인 경우
//            showToast("인증 성공")
//            UserInfo.tel = binding.phoneAuthEtPhoneNum.text.toString()
//            startActivity(Intent(this@PhoneAuthActivity, UserInfoActivity::class.java))
//            return
//        }
        Firebase.auth.signInWithCredential(phoneAuthCredential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    loginAgain()
                    Log.d("phoneTest",com.kosalaamInc.kosalaam.global.Application.user!!.email.toString())

                } else {
                    Toast.makeText(this@PhoneNumRegisterActivity,"Verify Fail",Toast.LENGTH_SHORT).show()
//                    binding.phoneAuthTvAuthNum.text =
//                        getString(R.string.auth_num_wrong_text)
//                    binding.phoneAuthTvAuthNum.setTextColor(
//                        ContextCompat.getColor(this@PhoneAuthActivity, R.color.red_FF5050)
//                    )
//                    binding.phoneAuthEtAuthNum.isEnabled = true
                }
            }
    }

    private fun resendVerificationCode(
        phoneNumber: String,
        token: PhoneAuthProvider.ForceResendingToken?
    ) {
        val optionsBuilder = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(phoneNumber)       // Phone number to verify
            .setTimeout(90L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(this)                 // Activity (for callback binding)
            .setCallbacks(callbacks)          // OnVerificationStateChangedCallbacks
        if (token != null) {
            optionsBuilder.setForceResendingToken(token) // callback's ForceResendingToken
        }
        PhoneAuthProvider.verifyPhoneNumber(optionsBuilder.build())
    }


    private var binding : ActivityPhoneNumRegisterBinding? = null

    private val viewModel : PhoneNumRegisterViewModel by lazy{
        ViewModelProvider(this).get(PhoneNumRegisterViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
        binding = DataBindingUtil.setContentView<ActivityPhoneNumRegisterBinding>(
            this, R.layout.activity_phone_num_register
        ).apply {
            lifecycleOwner = this@PhoneNumRegisterActivity
            phoneRegisterVm = viewModel
        }
        readCountryJson()
        initSpinnerArray()
        initListener()
        initObserve()
    }

    private fun initListener(){
        binding!!.etPhoneRegisterPhoneNum.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding!!.tvPhoneRegisterPhoneNum.setText(PhoneNumberUtils.formatNumber(binding!!.etPhoneRegisterPhoneNum.text.toString(), Locale.getDefault().getCountry()))
            }
            override fun afterTextChanged(s: Editable?) {
                if(s.toString().length>0){
                    binding!!.tvPhoneRegisterSendCode.background=getDrawable(R.drawable.login_mainoval)
                    binding!!.tvPhoneRegisterSendCode.isClickable=true
                }
                else{
                    binding!!.tvPhoneRegisterSendCode.background=getDrawable(R.drawable.login_defaultback)
                    binding!!.tvPhoneRegisterSendCode.isClickable=false
                }
            }
        })

        binding!!.etPhoneRegisterPhoneCode.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding!!.tvPhoneRegisterPhoneNum.setText(PhoneNumberUtils.formatNumber(binding!!.etPhoneRegisterPhoneNum.text.toString(), Locale.getDefault().getCountry()))
            }
            override fun afterTextChanged(s: Editable?) {
                if(s.toString().length>0){
                    binding!!.tvPhoneRegisterSubmit.background=getDrawable(R.drawable.login_mainoval)
                    binding!!.tvPhoneRegisterSubmit.isClickable=true
                }
                else{
                    binding!!.tvPhoneRegisterSubmit.background=getDrawable(R.drawable.login_defaultback)
                    binding!!.tvPhoneRegisterSubmit.isClickable=false
                }
            }
        })

        binding!!.tvPhoneRegisterSendCode.setOnClickListener {
            //TODO 국가마다 첫글자 0 들어가는지 확인
            Log.d(TAG,binding!!.tvPhoneRegisterCountryCode.text.toString()+binding!!.etPhoneRegisterPhoneNum.text.toString().substring(1,binding!!.etPhoneRegisterPhoneNum.text.toString().length))
            startPhoneNumberVerification(binding!!.tvPhoneRegisterCountryCode.text.toString()+
                    binding!!.etPhoneRegisterPhoneNum.text.toString().substring(1,binding!!.etPhoneRegisterPhoneNum.text.toString().length))
        }
        binding!!.tvPhoneRegisterSubmit.setOnClickListener {
            val phoneCredential = PhoneAuthProvider.getCredential(storedVerificationId,binding!!.etPhoneRegisterPhoneCode.text.toString())
            verifyPhoneNumberWithCode(phoneCredential)
        }
    }

    private fun initObserve(){
        viewModel.userData.observe(this, androidx.lifecycle.Observer {
            if(status==1){
                if(it){
                    startActivity(Intent(this,HostResistrationActivity::class.java))
                    HostResistrationActivity.phoneNum = binding!!.tvPhoneRegisterPhoneNum.text.toString()
                    this.finish()
                }
            }
            else{
                this.finish()
            }

        })
    }


    private fun initSpinnerArray(){
        val adapter = ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item, country!!.toList())
        binding!!.spinnerCountry.adapter=adapter
        binding!!.spinnerCountry.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long,
            ) {
               binding!!.tvPhoneRegisterCountryCode.text = code.get(position)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }
    }

    private fun readCountryJson(){
        val jsonString = assets.open("country.json").reader().readText()
        val jsonArray = JSONArray(jsonString)
        for (index in 0 until jsonArray.length())
        {
            val jsonObject = jsonArray.getJSONObject(index)
            country.add(jsonObject.getString("name"))
            code.add(jsonObject.getString("code"))
        }
    }

    private fun loginAgain(){
        if(com.kosalaamInc.kosalaam.global.Application.prefs.getString("platform","")=="facebook"){
            val credential = FacebookAuthProvider.getCredential(com.kosalaamInc.kosalaam.global.Application.prefs.getString("token",""))
            auth.signInWithCredential(credential)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        com.kosalaamInc.kosalaam.global.Application.user =auth.currentUser
                        goRegister()
                    } else {

                    }
                }

        }
        else if(com.kosalaamInc.kosalaam.global.Application.prefs.getString("platform","")=="google"){
            val credential = GoogleAuthProvider.getCredential(com.kosalaamInc.kosalaam.global.Application.prefs.getString("token",""), null)
            auth.signInWithCredential(credential)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Log.d("SplashAcitity","facebooksucess")
                        com.kosalaamInc.kosalaam.global.Application.user =auth.currentUser
                        goRegister()
                    } else {
                        Log.d("SplashAcitity","facebookfail")

                    }
                }

        }
        else if(com.kosalaamInc.kosalaam.global.Application.prefs.getString("platform","")=="email"){
            auth.createUserWithEmailAndPassword(com.kosalaamInc.kosalaam.global.Application.prefs.getString("userEmail",""), com.kosalaamInc.kosalaam.global.Application.prefs.getString("password",""))
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        com.kosalaamInc.kosalaam.global.Application.user =auth.currentUser
                        goRegister()
                        // Sign in success, update UI with the signed-in user's information
                    } else {

                    }
                }
        }
        else{
            Log.d("SplashAcitity","else")
            Log.d("splashInfo_platform","this"+ com.kosalaamInc.kosalaam.global.Application.prefs.getString("platform",""))
            Log.d("splashInfo_platform","this"+ com.kosalaamInc.kosalaam.global.Application.prefs.getString("token",""))
        }
    }

    private fun goRegister(){
        com.kosalaamInc.kosalaam.global.Application.prefs.setString("phoneNumber",binding!!.tvPhoneRegisterPhoneNum.text.toString())
        viewModel.putUserCertified(true,binding!!.tvPhoneRegisterPhoneNum.text.toString())
    }
}
