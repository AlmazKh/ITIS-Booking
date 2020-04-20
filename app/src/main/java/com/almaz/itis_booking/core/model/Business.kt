package com.almaz.itis_booking.core.model

data class Business(
        val id: Int,
        val date: String,
        val time: Time,
        val status: Status,
        val user: User?
)