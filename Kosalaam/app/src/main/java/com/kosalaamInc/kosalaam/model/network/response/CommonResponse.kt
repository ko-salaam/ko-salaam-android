package com.kosalaamInc.kosalaam.model.network.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class CommonResponse{

    @SerializedName("isMuslimFriendly")
    @Expose
    val isMuslimFriendly : Boolean = false

    @SerializedName("id")
    @Expose
    val id : String? = null

    @SerializedName("name")
    @Expose
    val name : String? = null

    @SerializedName("address")
    @Expose
    val address : String? = null

    @SerializedName("latitude")
    @Expose
    val latitude : Double? = null

    @SerializedName("longitude")
    @Expose
    val longitude : Double? = null

    @SerializedName("images")
    @Expose
    val images : ArrayList<String>? = null

    @SerializedName("placeType")
    @Expose
    val placeType : String? = null
}