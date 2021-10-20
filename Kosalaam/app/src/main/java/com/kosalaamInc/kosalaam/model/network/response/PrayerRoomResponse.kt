package com.kosalaamInc.kosalaam.model.network.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class PrayerRoomResponse{
    @SerializedName("address")
    @Expose
    val address : String? = null

    @SerializedName("detailInfo")
    @Expose
    val detailInfo : String? = null

    @SerializedName("id")
    @Expose
    val id : String? = null

    @SerializedName("images")
    @Expose
    val imagesId : ArrayList<String>? = null

    @SerializedName("isLiked")
    @Expose
    val isLiked : Boolean? = null

    @SerializedName("isParkingLot")
    @Expose
    val isPrakingLot : Boolean? = null

    @SerializedName("latitude")
    @Expose
    val latitude : Double? = null

    @SerializedName("likedCount")
    @Expose
    val likeCount : Int? = null

    @SerializedName("longitude")
    @Expose
    val longitude : Double? = null

    @SerializedName("name")
    @Expose
    val name : String? = null

    @SerializedName("openingHours")
    @Expose
    val phoneNumber : String? = null

    @SerializedName("placeType")
    @Expose
    val placeType : String? = null





}