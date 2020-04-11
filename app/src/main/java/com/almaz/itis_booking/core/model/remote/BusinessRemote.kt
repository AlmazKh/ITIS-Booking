package com.almaz.itis_booking.core.model.remote

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class BusinessRemote(
    @SerializedName("id")
    @Expose
    val id: Int,
    @SerializedName("date")
    @Expose
    val date: String,
    @SerializedName("time")
    @Expose
    val time: String,
    @SerializedName("status")
    @Expose
    val status: String,
    @SerializedName("cabinet")
    @Expose
    val cabinet: CabinetRemote,
    @SerializedName("usr")
    @Expose
    val usr: UserRemote
)