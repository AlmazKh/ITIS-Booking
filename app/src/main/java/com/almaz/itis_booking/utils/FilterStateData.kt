package com.almaz.itis_booking.utils

data class FilterStateData(
    val date: String,
    val time: List<String>,
    val bookedStatus: String,
    val floor: List<String>,
    val capacity: String
)