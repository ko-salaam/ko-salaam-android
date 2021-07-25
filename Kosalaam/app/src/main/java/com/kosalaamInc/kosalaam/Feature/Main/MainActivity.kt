package com.kosalaamInc.kosalaam.Feature.Main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.NavHostController

import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.kosalaamInc.kosalaam.Feature.Main.MyPageFragment.MyPageFragment
import com.kosalaamInc.kosalaam.R
import com.kosalaamInc.kosalaam.databinding.ActivityMainBinding

// bottom navigation view
class MainActivity : AppCompatActivity() {
    private lateinit var mBinding : ActivityMainBinding
    private lateinit var navController : NavController
    private lateinit var navHostFragment: NavHostFragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding!!.root)

        initNavigationUI()



    }

    private fun initNavigationUI(){
        // navigation host
        navHostFragment = supportFragmentManager.findFragmentById(R.id.fcv_main) as NavHostFragment
        // navigation controller
        navController = navHostFragment.navController
        NavigationUI.setupWithNavController(mBinding.bnvMain,navController)

    }

    override fun onDestroy() {
        super.onDestroy()

    }
//    fun mainBtEvent(){
//        var navHostFragment = supportFragmentManager.beginTransaction().replace(R.id.fcv_main,MyPageFragment()).commit()
//        NavigationUI.setupWithNavController(mBinding.bnvMain,navController)
//    }
    //mainFragment 이벤트를 여기로 전해줄수 있도록
}