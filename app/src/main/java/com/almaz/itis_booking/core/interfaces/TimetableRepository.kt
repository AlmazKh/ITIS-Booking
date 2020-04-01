package com.almaz.itis_booking.core.interfaces

import com.almaz.itis_booking.core.model.Timetable
import io.reactivex.Completable
import io.reactivex.Single

interface TimetableRepository {
    fun getTimetable(): Single<Timetable>
    fun bookCabinet(): Completable
}