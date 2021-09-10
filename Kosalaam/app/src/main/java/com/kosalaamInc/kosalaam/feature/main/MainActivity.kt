package com.kosalaamInc.kosalaam.feature.main

import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.content.pm.Signature
import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import androidx.navigation.NavController

import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.kosalaamInc.kosalaam.R
import com.kosalaamInc.kosalaam.databinding.ActivityMainBinding
import com.kosalaamInc.kosalaam.feature.main.myPageFragment.MyPageFragment
import com.kosalaamInc.kosalaam.feature.main.prayerRoomFragment.PrayerRoomFragment
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

// bottom navigation view
class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private lateinit var navController : NavController
    private lateinit var navHostFragment: NavHostFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        initNavigationUI()
        Log.d("CheckDpValue",getBottomNavHeight().toString())

    }


    private fun initNavigationUI(){
        // navigation host
        navHostFragment = supportFragmentManager.findFragmentById(R.id.fcv_main) as NavHostFragment
        // navigation controller
        navController = navHostFragment.navController
        NavigationUI.setupWithNavController(binding.bnvMain,navController)

    }

    override fun onDestroy() {
        super.onDestroy()

    }
    fun initObserve(){

    }

    fun mainBtEvent(){
        var navHostFragment = supportFragmentManager.beginTransaction().replace(R.id.fcv_main,
            MyPageFragment()).commit()
        NavigationUI.setupWithNavController(binding.bnvMain,navController)
    }

    fun getBottomNavHeight() {
        val resourceId : Int = resources.getIdentifier("design_bottom_navigation_height","dimen",this.packageName)
        var height : Int = 0
        if(resourceId>0){
            height =resources.getDimensionPixelSize(resourceId)
        }
        val desity : Float = resources.displayMetrics.density
        val dp = height/desity
        PrayerRoomFragment.margin = dp
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val imm: InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
        return true
    }


    //mainFragment 이벤트를 여기로 전해줄수 있도록
}