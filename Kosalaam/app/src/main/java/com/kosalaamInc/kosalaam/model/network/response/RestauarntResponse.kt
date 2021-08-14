package com.kosalaamInc.kosalaam.model.network.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class RestauarntResponse {

    @SerializedName("address")
    @Expose
    val address : String? = null

    @SerializedName("detailInfo")
    @Expose
    val detailInfo : String? = null

    @SerializedName("dishType")
    @Expose
    val dishType : String? = null
    @SerializedName("id")
    @Expose
    val id : Int = 0

    @SerializedName("imagesId")
    @Expose
    val imagesId : String? = null

    @SerializedName("isLinked")
    @Expose
    val isLinked : Boolean= false

    @SerializedName("isParkingLot")
    @Expose
    val isParkingLog : Boolean = false

    @SerializedName("latitude")
    @Expose
    val latitude : Float = 0.00000f

    @SerializedName("likeCount")
    @Expose
    val likeCount : Int = 0

    @SerializedName("longitude")
    @Expose
    val longitude : Float = 0.00000f

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

}
