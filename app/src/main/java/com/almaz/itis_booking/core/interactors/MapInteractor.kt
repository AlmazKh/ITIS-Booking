package com.almaz.itis_booking.core.interactors

import com.almaz.itis_booking.core.interfaces.TimetableRepository
import com.almaz.itis_booking.core.model.Cabinet
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MapInteractor
@Inject constructor(
    private val timetableRepository: TimetableRepository
) {

    fun getDataForMapWithFilter(
        date: String,
        time: String,
        floor: String
    ): Single<List<String>> =
        timetableRepository.getDataForMapWithFilter(
            date,
            time,
            floor
        )
            .subscribeOn(Schedulers.io())
}