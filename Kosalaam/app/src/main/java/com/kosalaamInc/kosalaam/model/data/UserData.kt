package com.kosalaamInc.kosalaam.model.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class UserData(

    @SerializedName("phoneNumber")
    @Expose
    val phoneNumber: String? = null,

    @SerializedName("name")
    @Expose
    val name: String? = null


)