package com.kosalaamInc.kosalaam.model.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class UserCertified(
    @SerializedName("isCertificated")
    val isCertificated : Boolean,

    @SerializedName("phoneNumber")
    val phoneNumber : String
)