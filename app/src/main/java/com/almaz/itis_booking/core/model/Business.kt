package com.almaz.itis_booking.core.model

import com.almaz.itis_booking.core.model.remote.CabinetRemote
import com.almaz.itis_booking.core.model.remote.UserRemote

data class Business(
    val id: Int?,
    val date: String,
    val time: Time,
    val status: Status,
    val user: User?
)