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
    val id : String? = null

    @SerializedName("imagesId")
    @Expose
    val imagesId : List<String>? = null

    @SerializedName("isLiked")
    @Expose
    val isLiked : Boolean= false

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
    @SerializedName("muslimFriendly")
    @Expose
    val muslimFriendly : String? = null

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
