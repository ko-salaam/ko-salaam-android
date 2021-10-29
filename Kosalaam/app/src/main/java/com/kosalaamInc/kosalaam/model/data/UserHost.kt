package com.kosalaamInc.kosalaam.model.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class UserHost(
    @SerializedName("isHost")
    val isHost : Boolean
)