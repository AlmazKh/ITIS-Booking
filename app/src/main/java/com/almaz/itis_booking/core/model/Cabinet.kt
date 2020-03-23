package com.almaz.itis_booking.core.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Cabinet (
    val id: Long,
    val number: String,
    val capacity: String,
    val status: Status,
    val statusAddition: String?
): Parcelable