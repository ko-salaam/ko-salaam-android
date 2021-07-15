package com.kosalaamInc.kosalaam.Feature.Main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.kosalaamInc.kosalaam.R
import com.kosalaamInc.kosalaam.databinding.ActivityMainBinding

// bottom navigation view
class MainActivity : AppCompatActivity() {

    private lateinit var mBinding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        // navigation host
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fcv_main) as NavHostFragment

        // navigation controller
        val navController = navHostFragment.navController

        NavigationUI.setupWithNavController(mBinding.bnvMain,navController)
    }
}