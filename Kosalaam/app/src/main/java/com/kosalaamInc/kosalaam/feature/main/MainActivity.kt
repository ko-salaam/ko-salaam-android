package com.kosalaamInc.kosalaam.feature.main

import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.content.pm.Signature
import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.util.Log
import androidx.navigation.NavController

import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.kosalaamInc.kosalaam.R
import com.kosalaamInc.kosalaam.databinding.ActivityMainBinding
import com.kosalaamInc.kosalaam.feature.main.myPageFragment.MyPageFragment
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
    //mainFragment 이벤트를 여기로 전해줄수 있도록
}