package com.almaz.itis_booking.core.model.remote

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class TimeRemote(
    @SerializedName("FourthClass")
    @Expose
    val fourthClass: String?,
    @SerializedName("FirstClass")
    @Expose
    val firstClass: String?,
    @SerializedName("ThirdClass")
    @Expose
    val thirdClass: String?,
    @SerializedName("FifthClass")
    @Expose
    val fifthClass: String?,
    @SerializedName("SecondClass")
    @Expose
    val secondClass: String?,
    @SerializedName("SixthClass")
    @Expose
    val sixthClass: String?
)