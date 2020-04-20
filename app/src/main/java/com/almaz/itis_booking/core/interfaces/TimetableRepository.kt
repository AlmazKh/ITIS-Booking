package com.almaz.itis_booking.core.interfaces

import com.almaz.itis_booking.core.model.Cabinet
import com.almaz.itis_booking.core.model.User
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single

interface TimetableRepository {
    fun getCabinetsFreeTime(date: String): Single<List<Cabinet>>
    fun getDataWithFilter(
        date: String,
        times: List<String>,
        bookedStatus: String,
        floors: List<String>,
        capacity: String,
        priority: Int
    ): Single<List<Cabinet>>
    fun bookCabinet(user: User, cabinet: Cabinet, time: String): Completable
}