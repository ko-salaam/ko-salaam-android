package com.kosalaamInc.kosalaam.feature.loginIn

import android.content.Intent
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.kosalaamInc.kosalaam.R
import com.kosalaamInc.kosalaam.databinding.ActivityLoginInBinding
import com.kosalaamInc.kosalaam.feature.Main.MainActivity
import com.kosalaamInc.kosalaam.feature.signUp.BiggerDotPasswordTransformationMethod

class LoginInActivity : AppCompatActivity(){
    private var binding : ActivityLoginInBinding? = null
    private lateinit var auth: FirebaseAuth
    val TAG : String = "LoginInActivity"

    private val viewModel :LoginInViewModel by lazy{
        ViewModelProvider(this).get(LoginInViewModel::class.java)
    }

    // Delete backstack after login

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
        binding = DataBindingUtil.setContentView<ActivityLoginInBinding>(
            this, R.layout.activity_login_in
        ).apply {
            lifecycleOwner = this@LoginInActivity
            loginInVm = viewModel
        }
        binding!!.loginEditPassword.transformationMethod = BiggerDotPasswordTransformationMethod
        initobserve()
    }
    public override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if(currentUser != null){
            //reload();
        }
    }


    private fun initobserve(){
        with(viewModel){
            email_text_after.observe(this@LoginInActivity, Observer {
                it.getContentIfNotHandled()?.let {
                    if(LoginInViewModel.emailCheck==true && LoginInViewModel.passWordCheck==true){
                        binding!!.tvLoginLoginbt.background = getDrawable(R.drawable.login_mainoval)
                        binding!!.tvLoginLoginbt.isClickable=true
                    }
                    else{
                        binding!!.tvLoginLoginbt.background = getDrawable(R.drawable.login_defaultback)
                        binding!!.tvLoginLoginbt.isClickable=false
                    }
                }

            })
            password_after.observe(this@LoginInActivity, Observer {
                it.getContentIfNotHandled()?.let {
                    if(LoginInViewModel.emailCheck==true && LoginInViewModel.passWordCheck==true){
                        binding!!.tvLoginLoginbt.background = getDrawable(R.drawable.login_mainoval)
                        binding!!.tvLoginLoginbt.isClickable=true
                    }
                    else{
                        binding!!.tvLoginLoginbt.background = getDrawable(R.drawable.login_defaultback)
                        binding!!.tvLoginLoginbt.isClickable=false
                    }
                }
            })
            passWordVisible.observe(this@LoginInActivity, Observer {
                it.getContentIfNotHandled()?.let {
                    if(LoginInViewModel.passwordVisible==false){
                        LoginInViewModel.passwordVisible=true
                        binding!!.loginOffeye.setImageDrawable(getDrawable(R.drawable.login_eye_on))
                        binding!!.loginEditPassword.transformationMethod = HideReturnsTransformationMethod.getInstance()
                    }
                    else{
                        LoginInViewModel.passwordVisible=false
                        binding!!.loginOffeye.setImageDrawable(getDrawable(R.drawable.login_eye_off))
                        binding!!.loginEditPassword.transformationMethod =BiggerDotPasswordTransformationMethod
                    }
                }
            })
            signIn_Bt.observe(this@LoginInActivity, Observer {
                it.getContentIfNotHandled()?.let {
                    signIn()
                }
            })

        }

    }
    private fun signIn(){
        try{
            auth.signInWithEmailAndPassword(LoginInViewModel.emailString, LoginInViewModel.passwordString)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Log.d(TAG, "signInWithEmail:success")
                        val user = auth.currentUser
                        updateUI(user)
                    } else {
                        Log.w(TAG, "signInWithEmail:failure", task.exception)
                        Toast.makeText(baseContext, "Authentication failed.",
                            Toast.LENGTH_SHORT).show()
                        updateUI(null)
                    }
                }
        }
        catch (e : FirebaseNetworkException){

        }
        catch (e: FirebaseAuthException){

        }


    }

    override fun onDestroy() {
        super.onDestroy()
        LoginInViewModel.emailCheck=false
        LoginInViewModel.passWordCheck=false
        LoginInViewModel.passwordVisible=false
        binding=null
    }
    private fun updateUI(user : FirebaseUser?){
        if(user!= null){
            startActivity(Intent(this,MainActivity::class.java))
        }
        else{

            // user is null
        }
    }

}