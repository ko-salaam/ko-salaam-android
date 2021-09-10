package com.kosalaamInc.kosalaam.feature.signUp

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GetTokenResult
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

import com.kosalaamInc.kosalaam.R
import com.kosalaamInc.kosalaam.util.SendMailVerification
import com.kosalaamInc.kosalaam.databinding.ActivitySignupBinding
import com.kosalaamInc.kosalaam.feature.login.LoginActivity
import com.kosalaamInc.kosalaam.feature.loginIn.LoginInActivity
import com.kosalaamInc.kosalaam.feature.main.MainActivity
import kotlinx.coroutines.Job
import kotlin.random.Random


class SignUpActivity : AppCompatActivity() {

    var UserSendJob: Job? = null

    private lateinit var auth: FirebaseAuth
    private var binding: ActivitySignupBinding? = null
    private lateinit var wrongAnim : Animation
    val TAG = "SignUpActivity"

    companion object{
        var EmailverifyCode = ""
    }

    private val viewModel: SignUpViewModel by lazy {
        ViewModelProvider(this).get(SignUpViewModel::class.java)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
        binding = DataBindingUtil.setContentView<ActivitySignupBinding>(
            this, R.layout.activity_signup
        ).apply {
            lifecycleOwner = this@SignUpActivity
            signUpVm = viewModel
        }
        binding!!.etSignupHide2.transformationMethod =BiggerDotPasswordTransformationMethod
        binding!!.loginEditPassword.transformationMethod =BiggerDotPasswordTransformationMethod
        wrongAnim = AnimationUtils.loadAnimation(applicationContext,R.anim.signup_wrong_anim)
        initViewObserve()

    }

    private fun createAccount(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val isNew: Boolean = task.result.additionalUserInfo!!.isNewUser
                    var user = auth.currentUser
                    if (isNew == true) {
                        var token: String? = null
                        user!!.getIdToken(true)
                            .addOnCompleteListener(object : OnCompleteListener<GetTokenResult?> {
                                override fun onComplete(task: Task<GetTokenResult?>) {
                                    if (task.isSuccessful()) {
                                        val idToken: String? = task.getResult()?.getToken()
                                        Log.d(TAG, idToken!!)
                                        token = idToken
                                        try {
                                            viewModel.signUp(idToken!!)
                                            initSignUpObserve(user)
                                        } catch (t: Throwable) {
                                            deleteUser(user!!)
                                            //Toast message
                                        }

                                    } else {
                                        //Toast message
                                        deleteUser(user!!)
                                    }
                                }
                            })
                    } else {
                        updateUI(user)
                    }
                    Toast.makeText(
                        baseContext, "Sign Up Success.",
                        Toast.LENGTH_SHORT
                    ).show()
                    Log.d(TAG, "signInWithCredential:success")

                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    Toast.makeText(
                        baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                    auth.signOut()
                    updateUI(null)
                }
            }
        // [END create_user_with_email]
    }

    private fun initViewObserve() {
        with(viewModel) {
            email_BtEvent.observe(this@SignUpActivity, Observer {
                it.getContentIfNotHandled()?.let {
                    when (SignUpViewModel.click) {
                        0 -> {
                            binding!!.tvSignupNext
                            binding!!.clSignupHide1.visibility = View.VISIBLE
                            binding!!.tvSignupNext.isClickable = false
                            binding!!.tvSignupNext.background =
                                getDrawable(R.drawable.login_defaultback)
                            binding!!.tvSignupNext.setText("Verify")
                            createEmailCode()
                            Log.d(TAG,getString(R.string.kosalaamGmail))
                            SendMailVerification().sendEmail("Kosalaam Verify Code",
                               "Kosalaam Verify Code is :  " + EmailverifyCode,SignUpViewModel.getEmail.toString(),getString(R.string.kosalaamGmail))
                            SignUpViewModel.click += 1
                        }
                        // add email check func
                        1 -> {
                            if (SignUpViewModel.verifyCheck == false) {
                                binding!!.tvSignupWrong.visibility = View.VISIBLE
                                binding!!.tvSignupWrong.startAnimation(wrongAnim)
                            } else {
                                binding!!.tvSignupWrong.visibility = View.GONE
                                binding!!.clSignupHide1.visibility = View.GONE
                                binding!!.tvSignupNext.isClickable = false
                                binding!!.tvSignupNext.background =
                                    getDrawable(R.drawable.login_defaultback)
                                binding!!.etLoginEditEmail.visibility = View.GONE
                                binding!!.viewLoginView1.visibility = View.GONE
                                binding!!.clSignupHide2.visibility = View.VISIBLE
                                binding!!.tvSignupNext.setText("Sign in")

                                SignUpViewModel.click += 1
                            }
                        }
                        2-> {
                            createAccount(SignUpViewModel.getEmail!!,SignUpViewModel.getPassword!!)
                        }
                    }
                }
            })
            edit_email.observe(this@SignUpActivity, Observer {
                it.getContentIfNotHandled()?.let {
                    if (SignUpViewModel.emailCheck == true && SignUpViewModel.click == 0) {
                        binding!!.tvSignupNext.background = getDrawable(R.drawable.login_mainoval)
                        binding!!.tvSignupNext.isClickable = true
                    } else {
                        binding!!.tvSignupNext.isClickable = false
                        binding!!.tvSignupNext.background =
                            getDrawable(R.drawable.login_defaultback)

                    }
                }
            })
            email_check.observe(this@SignUpActivity, Observer {
                it.getContentIfNotHandled()?.let {
                    if (SignUpViewModel.emailCheck == true && SignUpViewModel.click == 0) {
                        binding!!.tvSignupNext.background = getDrawable(R.drawable.login_mainoval)
                        binding!!.tvSignupNext.isClickable = true
                    } else {
                        binding!!.tvSignupNext.isClickable = false
                        binding!!.tvSignupNext.background =
                            getDrawable(R.drawable.login_defaultback)

                    }
                }
            })
            verify_check_after.observe(this@SignUpActivity, Observer {
                it.getContentIfNotHandled()?.let {
                    if (it.length == 6 && SignUpViewModel.click == 1) {
                        binding!!.tvSignupNext.background = getDrawable(R.drawable.login_mainoval)
                        binding!!.tvSignupNext.isClickable = true
                    } else {
                        binding!!.tvSignupNext.isClickable = false
                        binding!!.tvSignupNext.background =
                            getDrawable(R.drawable.login_defaultback)
                    }
                }
            })
            passWordVisible.observe(this@SignUpActivity, Observer {
                it.getContentIfNotHandled()?.let {
                    if(SignUpViewModel.passwordVisible==false){
                        SignUpViewModel.passwordVisible=true
                        binding!!.loginOffeye.setImageDrawable(getDrawable(R.drawable.login_eye_on))
                        binding!!.loginEditPassword.transformationMethod = HideReturnsTransformationMethod.getInstance()
                    }
                    else{
                        SignUpViewModel.passwordVisible=false
                        binding!!.loginOffeye.setImageDrawable(getDrawable(R.drawable.login_eye_off))
                        binding!!.loginEditPassword.transformationMethod =BiggerDotPasswordTransformationMethod
                    }

                }
            })
            passWordVisible2.observe(this@SignUpActivity, Observer {
                it.getContentIfNotHandled()?.let {
                    if(SignUpViewModel.passwordVisible2==false){
                        SignUpViewModel.passwordVisible2=true
                        binding!!.ivSignupOffeyeHide2.setImageDrawable(getDrawable(R.drawable.login_eye_on))
                        binding!!.etSignupHide2.transformationMethod = HideReturnsTransformationMethod.getInstance()
                    }
                    else{
                        SignUpViewModel.passwordVisible2=false
                        binding!!.ivSignupOffeyeHide2.setImageDrawable(getDrawable(R.drawable.login_eye_off))
                        binding!!.etSignupHide2.transformationMethod =BiggerDotPasswordTransformationMethod
                    }
                }
            })
            password_after.observe(this@SignUpActivity, Observer {
                it.getContentIfNotHandled()?.let {
                    if(SignUpViewModel.passWordCheck==true && it==SignUpViewModel.passWordText2){
                        binding!!.tvSignupNext.background = getDrawable(R.drawable.login_mainoval)
                        binding!!.tvSignupNext.isClickable = true
                    }
                    else{
                        binding!!.tvSignupNext.background = getDrawable(R.drawable.login_defaultback)
                        binding!!.tvSignupNext.isClickable = false
                    }
                }
            })
            passwordCheck_after.observe(this@SignUpActivity, Observer {
                it.getContentIfNotHandled()?.let {
                    if (SignUpViewModel.passWordCheck2==true && it ==SignUpViewModel.passWordText){
                        binding!!.tvSignupNext.background = getDrawable(R.drawable.login_mainoval)
                        binding!!.tvSignupNext.isClickable = true
                    }
                    else{
                        binding!!.tvSignupNext.background = getDrawable(R.drawable.login_defaultback)
                        binding!!.tvSignupNext.isClickable = false
                    }
                }
            })
            already_BtEvent.observe(this@SignUpActivity, Observer {
                it.getContentIfNotHandled()?.let{
                    val intent = Intent(this@SignUpActivity,LoginInActivity::class.java)
                    startActivity(intent)
                    this@SignUpActivity.finish()
                }
            })
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        this.finish()
        SignUpViewModel.passwordVisible2=false
        SignUpViewModel.passwordVisible=false
        SignUpViewModel.click = 0
        SignUpViewModel.emailCheck = false
        SignUpViewModel.verifyCheck = false
        SignUpViewModel.passWordCheck = false
        SignUpViewModel.passWordCheck2 = false
        binding = null
    }

    private fun createEmailCode() {
        EmailverifyCode=""
        val codelist = listOf<String>("0","1","2","3","4","5","6","7","8","9")
        for(i in 0..5){
            val random  = Random.nextInt(10)
            EmailverifyCode += codelist[random]
        }
    }

    private fun updateUI(user: FirebaseUser?) {
        if (user != null) {
            startActivity(Intent(this, LoginInActivity::class.java))
        } else {
            // how to show
        }
    }

    @Suppress("DEPRECATION")
    private fun checkInternet(): Boolean {
        val cm = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
        val isConnected: Boolean = activeNetwork?.isConnectedOrConnecting == true
        return isConnected
    }

    private fun initSignUpObserve(user: FirebaseUser?) {
        viewModel.signUpBoolean.observe(this, Observer<Boolean> {
            Log.d("CheckBoolean", it.toString())
            if (it == true) {
                updateUI(user)
            } else {
                deleteUser(user!!)
            }
        })
    }

    private fun deleteUser(user: FirebaseUser?) {
        user!!.delete()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(LoginActivity.TAG, "User account deleted.")
                }
            }
    }

}

object BiggerDotPasswordTransformationMethod : PasswordTransformationMethod() {

    override fun getTransformation(source: CharSequence, view: View): CharSequence {
        return PasswordCharSequence(super.getTransformation(source, view))
    }

    private class PasswordCharSequence(
        val transformation: CharSequence
    ) : CharSequence by transformation {
        override fun get(index: Int): Char = if (transformation[index] == DOT) {
            BIGGER_DOT
        } else {
            transformation[index]
        }
    }

    private const val DOT = '\u2022'
    private const val BIGGER_DOT = '●'
}