package com.almaz.itis_booking.data.api

import com.almaz.itis_booking.core.model.remote.BusinessRemote
import com.almaz.itis_booking.core.model.remote.FreeTimeRemote
import com.almaz.itis_booking.core.model.remote.UserRemote
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface BookingApi {

    @GET("/timetable/free")
    fun getCabinetFreeTimeByDate(@Query("date") date: String): Observable<List<FreeTimeRemote>>

    @GET("/timetable/free/filtered")
    fun getCabinetFreeTimeByFilter(
        @Query("date") date: String,
        @Query("times") times: List<String>,
        @Query("floors") floors: List<String>,
        @Query("capacity") capacity: String
    ): Observable<List<FreeTimeRemote>>

    @GET("/business/filtered")
    fun getCabinetBusinessByFilter(
        @Query("date") date: String,
        @Query("times") times: List<String>,
        @Query("floors") floors: List<String>,
        @Query("capacity") capacity: String,
        @Query("priority") priority: Int
    ): Observable<List<BusinessRemote>>

    @GET("/user")
    fun getUserByEmail(
        @Query("email") email: String
    ): Observable<UserRemote>

    @POST("/book")
    fun bookCabinet(@Body businessRemote: BusinessRemote) : Observable<BusinessRemote>
}