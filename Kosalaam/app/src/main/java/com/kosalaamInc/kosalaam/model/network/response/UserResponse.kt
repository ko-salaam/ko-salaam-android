package com.kosalaamInc.kosalaam.model.network.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class UserResponse{

    @SerializedName("id")
    @Expose
    val id : Int = 0

    @SerializedName("name")
    @Expose
    val name : String? = null

    @SerializedName("firebaseUuid")
    @Expose
    val firebaseUuid : String? = null

    @SerializedName("isCertificated")
    @Expose
    val isCertificated : Boolean = false

    @SerializedName("phoneNumber")
    @Expose
    val phoneNumber : String? = null

    @SerializedName("email")
    @Expose
    val email : String? = null

    @SerializedName("profileImg")
    @Expose
    val profileImg : String? = null

    @SerializedName("isHost")
    @Expose
    val isHost : Boolean = false

}