package com.kosalaamInc.kosalaam.model.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class PraySuppliesData (
    @SerializedName("isKoran")
    @Expose
    val isKoran : Boolean = false,

    @SerializedName("isMat")
    @Expose
    val isMat : Boolean = false,

    @SerializedName("isQibla")
    @Expose
    val isQibla : Boolean = false,

    @SerializedName("isWashingRoom")
    @Expose
    val isWashingRoom : Boolean = false
)