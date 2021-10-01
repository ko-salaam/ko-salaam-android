package com.kosalaamInc.kosalaam.model.network.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class HotelResponse {

    @SerializedName("address")
    @Expose
    val address : String? = null

    @SerializedName("detailInfo")
    @Expose
    val detailInfo : String? = null

    @SerializedName("id")
    @Expose
    val id : Int = 0

    @SerializedName("imagesId")
    @Expose
    val imagesId : String? = null

    @SerializedName("isLiked")
    @Expose
    val isLiked : Boolean= false

    @SerializedName("isMuslimFriendly")
    @Expose
    val isMuslimFriendly : Boolean = false

    @SerializedName("isParkingLot")
    @Expose
    val isParkingLog : Boolean = false

    @SerializedName("latitude")
    @Expose
    val latitude : Double = 0.00000000

    @SerializedName("likedCount")
    @Expose
    val likedCount : Int = 0

    @SerializedName("longitude")
    @Expose
    val longitude : Double = 0.00000000

    // muslim friendly

    @SerializedName("name")
    @Expose
    val name : String? = null

    @SerializedName("openingHours")
    @Expose
    val openingHours : String? = null

    @SerializedName("phoneNumber")
    @Expose
    val phoneNumber : String? = null

    @SerializedName("praySupplies")
    @Expose
    val praySupplies : PraySupplies? = null

    @SerializedName("uuid")
    @Expose
    val uuid : String? = null
}

class PraySupplies{

    @SerializedName("isKoran")
    @Expose
    val isKoran : Boolean = false

    @SerializedName("isMat")
    @Expose
    val isMat : Boolean = false

    @SerializedName("isQibla")
    @Expose
    val isQibla : Boolean = false

    @SerializedName("isWashingRoom")
    @Expose
    val isWashingRoom : Boolean = false
}