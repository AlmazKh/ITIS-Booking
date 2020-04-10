package com.almaz.itis_booking.data.api

import com.almaz.itis_booking.core.model.remote.TimetableRemote
import io.reactivex.Observable
import retrofit2.http.GET

interface BookingApi {
    @GET("/timetable")
    fun getTimetable(): Observable<TimetableRemote>
}