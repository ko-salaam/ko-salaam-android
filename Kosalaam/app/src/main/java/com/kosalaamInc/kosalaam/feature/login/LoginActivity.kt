package com.kosalaamInc.kosalaam.feature.login

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.facebook.*
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.kosalaamInc.kosalaam.R
import com.kosalaamInc.kosalaam.databinding.ActivityLoginBinding
import com.kosalaamInc.kosalaam.feature.loginIn.LoginInActivity
import com.kosalaamInc.kosalaam.feature.main.MainActivity
import com.kosalaamInc.kosalaam.feature.signUp.SignUpActivity
import com.kosalaamInc.kosalaam.global.Application
import java.util.*


class LoginActivity : AppCompatActivity() {

    companion object {
        val TAG = "loginActivity"
    }

    private var binding: ActivityLoginBinding? = null
    private val RC_SIGN_IN = 9001
    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var callbackManager: CallbackManager

    private val viewModel: LoginViewModel by lazy {
        ViewModelProvider(this).get(LoginViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LoginManager.getInstance().logOut()
        auth = Firebase.auth
        binding = DataBindingUtil.setContentView<ActivityLoginBinding>(
            this, R.layout.activity_login
        ).apply {
            lifecycleOwner = this@LoginActivity
            loginVm = viewModel
        }
        initObserve()
    }

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
    }


    private fun initObserve() {
        with(viewModel) {
            signIn_Bt.observe(this@LoginActivity, Observer {
                it.getContentIfNotHandled()?.let {
                    signInBtInit()
                }
            })
            signUp_Bt.observe(this@LoginActivity, Observer {
                it.getContentIfNotHandled()?.let {
                    signUpBtInit()
                }
            })
            facebook_Bt.observe(this@LoginActivity, Observer {
                it.getContentIfNotHandled()?.let {
                    if (checkInternet() == true) {
                        facebookBtInit()
                    } else {
                        Toast.makeText(this@LoginActivity,
                            "Check your Internet",
                            Toast.LENGTH_SHORT).show()
                    }

                }
            })
            google_Bt.observe(this@LoginActivity, Observer {
                it.getContentIfNotHandled()?.let {
                    if (checkInternet() == true) {
                        googleBtInit()
                    } else {
                        Toast.makeText(this@LoginActivity,
                            "Check your Internet",
                            Toast.LENGTH_SHORT).show()
                    }

                }
            })
        }

    }

    private fun signInBtInit() {
        val intent = Intent(this, LoginInActivity::class.java)
        startActivity(intent)
    }

    private fun facebookBtInit() {
        callbackManager = CallbackManager.Factory.create()
        facebookLogin()
    }

    private fun googleBtInit() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.google_OAuth))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)
        GooglesignIn()
    }

    private fun signUpBtInit() {
        val intent = Intent(this, SignUpActivity::class.java)
        startActivity(intent)
    }

    @Suppress("DEPRECATION")
    private fun GooglesignIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    @Suppress("DEPRECATION")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.d(TAG, "onActivityResult")
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)!!
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.id)
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e)
            }

        } else {
            callbackManager.onActivityResult(requestCode, resultCode, data)
        }
    }


    private fun facebookLogin() {
        LoginManager.getInstance()
            .logInWithReadPermissions(this, Arrays.asList("public_profile", "email"))
        LoginManager.getInstance().registerCallback(callbackManager, object :
            FacebookCallback<LoginResult> {
            override fun onSuccess(loginResult: LoginResult) {
                Log.d(TAG, "facebook:onSuccess:$loginResult")
                handleFacebookAccessToken(loginResult.accessToken)
            }

            override fun onCancel() {
                Log.d(TAG, "facebook:onCancel")
            }

            override fun onError(error: FacebookException) {
                Log.d(TAG, "facebook:onError", error)
            }
        })
    }

    private fun handleFacebookAccessToken(token: AccessToken) {
        Log.d(TAG, "handleFacebookAccessToken:$token")

        val credential = FacebookAuthProvider.getCredential(token.token)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val isNew: Boolean = task.result.additionalUserInfo!!.isNewUser
                    var user = auth.currentUser
                    Application.prefs.setString("platform","facebook")
                    Application.prefs.setString("token",token.token)
                    if (isNew == true) {
                        user!!.getIdToken(true)
                            .addOnCompleteListener(object : OnCompleteListener<GetTokenResult?> {
                                override fun onComplete(task: Task<GetTokenResult?>) {
                                    if (task.isSuccessful()) {
                                        val idToken: String? = task.getResult()?.getToken()
                                        Log.d(TAG, idToken!!)

                                        try {
                                            viewModel.signUp(idToken!!)
                                            initSignUpObserve(user)
                                        }
                                        catch (t: Throwable) {
                                            Application.prefs.setString("platform","")
                                            Application.prefs.setString("token","")
                                            deleteUser(user!!)
                                            //Toast message
                                        }

                                    } else {
                                        //Toast message
                                        Application.prefs.setString("platform","")
                                        Application.prefs.setString("token","")
                                        deleteUser(user!!)
                                    }
                                }
                            })
                    } else {
                        updateUI(user)
                    }
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
    }

    fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    var token: String? = null
                    val isNew: Boolean = task.result.additionalUserInfo!!.isNewUser
                    Application.prefs.setString("platform","google")
                    Application.prefs.setString("token",idToken)
                    if (isNew == true) {
                        var token: String? = null
                        user!!.getIdToken(true)
                            .addOnCompleteListener(object : OnCompleteListener<GetTokenResult?> {
                                override fun onComplete(task: Task<GetTokenResult?>) {
                                    if (task.isSuccessful()) {
                                        val idToken1: String? = task.getResult()?.getToken()
                                        Log.d(TAG, idToken1!!)
                                        token = idToken1
                                        try {
                                            viewModel.signUp(idToken1!!)
                                            initSignUpObserve(user)

                                        } catch (t: Throwable) {
                                            deleteUser(user!!)
                                            Application.prefs.setString("platform","")
                                            Application.prefs.setString("token","")
                                            //Toast message
                                        }

                                    } else {
                                        //toast message
                                        Application.prefs.setString("platform","")
                                        Application.prefs.setString("token","")
                                        deleteUser(user!!)
                                    }
                                }
                            })
                    } else {
                        updateUI(user)
                    }
                } else {
                    // Toast message
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    updateUI(null)
                }
            }
    }

    private fun updateUI(user: FirebaseUser?) {
        if (user != null) {
            startActivity(Intent(this, MainActivity::class.java))
        } else {
            Toast.makeText(this,"login failed, Try later",Toast.LENGTH_SHORT).show()
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
                Application.user = user
                updateUI(user)

            } else {
                Toast.makeText(this,"login failed, Try later",Toast.LENGTH_SHORT).show()
                deleteUser(user!!)
                Application.prefs.setString("platform","")
                Application.prefs.setString("token","")
            }
        })
    }

    private fun deleteUser(user: FirebaseUser?) {
        user!!.delete()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "User account deleted.")
                }
                else{

                }
            }
    }

}