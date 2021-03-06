package com.almaz.itis_booking.core.model.remote

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CabinetRemote (
    @SerializedName("id")
    @Expose
    val id: Int,
    @SerializedName("number")
    @Expose
    val number: Int,
    @SerializedName("capacity")
    @Expose
    val capacity: Int
)