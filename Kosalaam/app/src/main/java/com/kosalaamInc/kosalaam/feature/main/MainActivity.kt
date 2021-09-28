package com.kosalaamInc.kosalaam.feature.main

import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.content.pm.Signature
import android.content.res.Resources
import android.graphics.Point
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.util.DisplayMetrics
import android.util.Log
import android.view.MenuItem
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import androidx.core.view.forEach
import androidx.navigation.NavController
import androidx.navigation.findNavController

import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
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
    companion object{
        var desity : Float = 0.0F
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        desity= resources.displayMetrics.density
        initNavigationUI()
        getBottomNavHeight()
        bottomNavitemClick()
        getDisplayHeightPixel()

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

    fun getBottomNavHeight() {
        val resourceId : Int = resources.getIdentifier("design_bottom_navigation_height","dimen",this.packageName)
        var height : Int = 0
        if(resourceId>0){
            height =resources.getDimensionPixelSize(resourceId)
        }
        val dp = height/desity
        PrayerRoomFragment.margin = dp
    }


    // editText 외부 클릭시 keyboard 내림
    override fun onTouchEvent(event: MotionEvent): Boolean {
        val imm: InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
        return true
    }

    fun getDisplayHeightPixel(){
        val display = windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)
        PrayerRoomFragment.displayHeightDp = (size.y/desity).toInt()
    }

    private fun bottomNavitemClick() {
        binding!!.bnvMain.setOnNavigationItemSelectedListener {
            if(it.itemId!= binding!!.bnvMain.selectedItemId){
                NavigationUI.onNavDestinationSelected(it,navController)
            }
            true
        }
    }

    //mainFragment 이벤트를 여기로 전해줄수 있도록
}