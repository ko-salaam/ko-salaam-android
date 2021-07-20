package com.kosalaamInc.kosalaam.Util

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices


class LocationUtil{
    private var fusedLocationProviderClient: FusedLocationProviderClient ?= null
    private var location : MutableLiveData<Location> = MutableLiveData()

    // using singleton pattern to get the locationProviderClient
    fun getInstance(appContext: Context): FusedLocationProviderClient{
        if(fusedLocationProviderClient == null)
            fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(appContext)
        return fusedLocationProviderClient!!
    }

    // TODO permission check
    @SuppressLint("MissingPermission")
    fun getLocation() : LiveData<Location> {
        fusedLocationProviderClient!!.lastLocation
            .addOnSuccessListener {loc: Location? ->
                location.value = loc

            }

        return location
    }

}