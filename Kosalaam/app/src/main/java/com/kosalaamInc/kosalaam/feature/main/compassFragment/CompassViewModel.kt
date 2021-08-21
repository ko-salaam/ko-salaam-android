package com.kosalaamInc.kosalaam.feature.main.compassFragment

import android.location.Location
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CompassViewModel : ViewModel(){

    val permissionRequest = MutableLiveData<String>() // check location permission
    val locationRequest = MutableLiveData<Location>()

    fun onPermissionResult(permission: String, granted: Boolean) {
        if (granted==true){

        }
        else{

        }
    }
    private fun getLocation(){

    }



}