package com.almaz.itis_booking.core.interactors

import com.almaz.itis_booking.core.interfaces.TimetableRepository
import com.almaz.itis_booking.core.model.Cabinet
import com.almaz.itis_booking.core.model.Timetable
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class TimetableInteractor
@Inject constructor(
    private val timetableRepository: TimetableRepository
) {
    fun getTimetable(): Single<Timetable> =
        timetableRepository.getTimetable()
            .subscribeOn(Schedulers.io())

    fun getCabinetsFreeTime(date: String): Single<List<Cabinet>> =
        timetableRepository.getCabinetsFreeTime(date)
            .subscribeOn(Schedulers.io())

    fun bookCabinet(): Completable =
        timetableRepository.bookCabinet()
            .subscribeOn(Schedulers.io())
}
