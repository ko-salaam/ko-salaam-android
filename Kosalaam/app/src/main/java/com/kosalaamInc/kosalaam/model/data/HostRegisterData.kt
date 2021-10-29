package com.kosalaamInc.kosalaam.model.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.kosalaamInc.kosalaam.model.network.response.PraySupplies

data class HostRegisterData (

    @SerializedName("address")
    @Expose
    val address : String,

    @SerializedName("detailInfo")
    @Expose
    val detailInfo : String? = null,

    @SerializedName("hostId")
    @Expose
    val hostId : Int? = 0,

    @SerializedName("id")
    @Expose
    val id : String? = null,

    @SerializedName("isKosalaamRoom")
    @Expose
    val isKosalaamRoom : Boolean? = false,

    @SerializedName("isLiked")
    @Expose
    val isLiked : Boolean? = null,

    @SerializedName("isParkingLot")
    @Expose
    val isParkingLot : Boolean? = null,

    @SerializedName("latitude")
    @Expose
    val latitude : Double? = null,

    @SerializedName("likedCount")
    @Expose
    val likeCount : Int? = null,

    @SerializedName("longitude")
    @Expose
    val longitude : Double? = null,

    @SerializedName("managingType")
    @Expose
    val managingType : String? = null,

    @SerializedName("name")
    @Expose
    val name : String? = null,

    @SerializedName("openingHours")
    @Expose
    val openingHours : String? = null,

    @SerializedName("phoneNumber")
    @Expose
    val phoneNumber : String? = null,

    @SerializedName("placeType")
    @Expose
    val placeType : String? = null,

    @SerializedName("praySupplies")
    @Expose
    val praySupplies : PraySupplies? = null,

    @SerializedName("price")
    @Expose
    val price : Int? = 0
)