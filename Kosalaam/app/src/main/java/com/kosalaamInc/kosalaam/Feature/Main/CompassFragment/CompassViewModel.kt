package com.kosalaamInc.kosalaam.Feature.Main.CompassFragment

import android.location.Location
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CompassViewModel : ViewModel(){
    val permissionRequest = MutableLiveData<String>() // check location permission
    val locationRequest = MutableLiveData<Location>()

    fun onPermissionResult(permission: String, granted: Boolean) {
        TODO("whatever you need to do")
        if (granted==true){

        }
        else{

        }
    }
    private fun getLocation(){

    }



}