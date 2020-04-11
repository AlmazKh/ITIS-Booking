package com.almaz.itis_booking.core.model.remote

import com.almaz.itis_booking.core.model.Cabinet
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class FreeTimeRemote(
    @SerializedName("id")
    @Expose
    val id: Int,
    @SerializedName("time")
    @Expose
    val time: String,
    @SerializedName("cabinet")
    @Expose
    val cabinet: Cabinet
)