package com.kosalaamInc.kosalaam.model.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class EditUserData(

    @SerializedName("isCertificated")
    @Expose
    val isCertificated: Boolean = false,

    @SerializedName("name")
    @Expose
    val name: String? = null,

    @SerializedName("phoneNumber")
    @Expose
    val phoneNumber: String? = null,

    @SerializedName("email")
    @Expose
    val email: String? = null
)