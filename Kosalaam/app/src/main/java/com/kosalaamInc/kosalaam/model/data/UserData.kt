package com.kosalaamInc.kosalaam.model.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class UserData(

    @SerializedName("id")
    @Expose
    val id: Int = 0,

    @SerializedName("firebaseUuid")
    @Expose
    val firebaseUuid: String? = null,

    @SerializedName("isCertificated")
    @Expose
    val isCertificated: Boolean = false,

    @SerializedName("phoneNumber")
    @Expose
    val phoneNumber: String? = null,

    @SerializedName("email")
    @Expose
    val email: String? = null,

    @SerializedName("profileImg")
    @Expose
    val profileImg: String? = null,

    @SerializedName("isHost")
    @Expose
    val isHost: Boolean = false

)