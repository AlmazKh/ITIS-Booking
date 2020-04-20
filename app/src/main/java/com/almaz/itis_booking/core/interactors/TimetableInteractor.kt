package com.almaz.itis_booking.core.interactors

import com.almaz.itis_booking.core.interfaces.TimetableRepository
import com.almaz.itis_booking.core.interfaces.UserRepository
import com.almaz.itis_booking.core.model.Cabinet
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class TimetableInteractor
@Inject constructor(
    private val timetableRepository: TimetableRepository,
    private val userRepository: UserRepository
) {

    fun getCabinetsFreeTime(date: String): Single<List<Cabinet>> =
        timetableRepository.getCabinetsFreeTime(date)
            .subscribeOn(Schedulers.io())

    fun getDataWithFilter(
        date: String,
        time: List<String>,
        bookedStatus: String,
        floor: List<String>,
        capacity: String
    ): Single<List<Cabinet>> =
        userRepository.getCurrentUserPriorityValue()
            .flatMap {
                timetableRepository.getDataWithFilter(
                    date,
                    time,
                    bookedStatus,
                    floor,
                    capacity,
                    it
                )
            }

            .subscribeOn(Schedulers.io())

    fun bookCabinet(cabinet: Cabinet, time: String): Completable =
        userRepository.getCurrentUser()
            .flatMapCompletable {
                timetableRepository.bookCabinet(it, cabinet, time)
            }
            .subscribeOn(Schedulers.io())
}
