package com.almaz.itis_booking.core.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
data class Cabinet (
    val id: Int,
    val number: String,
    val capacity: String,
    val statusAddition: String?,
    val business: @RawValue List<Business>
): Parcelable