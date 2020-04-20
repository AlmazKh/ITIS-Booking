package com.almaz.itis_booking.core.model.remote

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class UserRemote(
    @SerializedName("id")
    @Expose
    val id: Int,
    @SerializedName("name")
    @Expose
    val name: String,
    @SerializedName("institute")
    @Expose
    val institute: String,
    @SerializedName("groupNumber")
    @Expose
    val groupNumber: String,
    @SerializedName("priority")
    @Expose
    val priority: Int,
    @SerializedName("email")
    @Expose
    val email: String,
    @SerializedName("photo")
    @Expose
    val photo: String?
)
