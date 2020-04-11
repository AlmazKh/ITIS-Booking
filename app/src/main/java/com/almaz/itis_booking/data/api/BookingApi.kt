package com.almaz.itis_booking.data.api

import com.almaz.itis_booking.core.model.remote.FreeTimeRemote
import com.almaz.itis_booking.core.model.remote.TimetableRemote
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface BookingApi {
    @GET("/timetable")
    fun getTimetable(): Observable<TimetableRemote>

    @GET("/timetable/date")
    fun getCabinetFreeTimeByDate(@Query("date") date: String): Observable<List<FreeTimeRemote>>

}