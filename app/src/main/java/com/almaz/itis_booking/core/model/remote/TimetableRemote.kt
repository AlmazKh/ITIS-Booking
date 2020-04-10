package com.almaz.itis_booking.core.model.remote

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class TimetableRemote(

    @SerializedName("cabinets")
    @Expose
    val cabinetRemotes: List<CabinetRemote>?
)
