package com.almaz.itis_booking.core.model.remote

import android.os.Parcelable
import com.almaz.itis_booking.core.model.Status
import com.almaz.itis_booking.core.model.Time
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class CabinetRemote (
    @SerializedName("id")
    @Expose
    val id: Long,
    @SerializedName("number")
    @Expose
    val number: Int,
    @SerializedName("capacity")
    @Expose
    val capacity: Int,
    @SerializedName("business")
    @Expose
    val business: TimeRemote

)