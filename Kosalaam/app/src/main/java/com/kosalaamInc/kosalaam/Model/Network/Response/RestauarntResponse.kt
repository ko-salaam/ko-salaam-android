package com.kosalaamInc.kosalaam.Model.Network.Response

import com.google.gson.annotations.SerializedName

class RestauarntResponse {

    @SerializedName("address")
    val address : String? = null
    @SerializedName("detailInfo")
    val detailInfo : String? = null
    @SerializedName("dishType")
    val dishType : String? = null
    @SerializedName("id")
    val id : Int = 0
    @SerializedName("imagesId")
    val imagesId : String? = null
    @SerializedName("isLinked")
    val isLinked : Boolean= false
    @SerializedName("isParkingLot")
    val isParkingLog : Boolean = false
    @SerializedName("latitude")
    val latitude : Float = 0.00000f
    @SerializedName("likeCount")
    val likeCount : Int = 0
    @SerializedName("longitude")
    // muslim friendly
    val longitude : Float = 0.00000f
    @SerializedName("name")
    val name : String? = null
    @SerializedName("openingHours")
    val openingHours : String? = null
    @SerializedName("phoneNumber")
    val phoneNumber : String? = null

}
