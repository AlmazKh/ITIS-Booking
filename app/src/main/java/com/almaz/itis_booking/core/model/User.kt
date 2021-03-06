package com.almaz.itis_booking.core.model

data class User (
    val id: Int,
    val name: String,
    val institute: String,
    val groupNumber: String,
    val priority: String,
    val email: String?,
    val photo: String?
)