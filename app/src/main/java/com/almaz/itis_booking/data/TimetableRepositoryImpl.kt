package com.almaz.itis_booking.data

import com.almaz.itis_booking.core.interfaces.TimetableRepository
import com.almaz.itis_booking.core.model.Cabinet
import com.almaz.itis_booking.core.model.Status
import com.almaz.itis_booking.core.model.Time
import com.almaz.itis_booking.core.model.Timetable
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class TimetableRepositoryImpl
@Inject constructor() : TimetableRepository {

    override fun getTimetable(): Single<Timetable> {
        return Single.just(
            Timetable(
                listOf(
                    Cabinet(
                        1, "1301", "20", mapOf(
                            Time.SecondClass to Status.Free,
                            Time.ThirdClass to Status.Booked,
                            Time.FourthClass to Status.Free,
                            Time.FifthClass to Status.Free,
                            Time.SixthClass to Status.Booked
                        ), "some addition 10:10-12:00"
                    ),
                    Cabinet(
                        2, "1302", "30", mapOf(
                            Time.SecondClass to Status.Free,
                            Time.ThirdClass to Status.Booked,
                            Time.FourthClass to Status.Free,
                            Time.FifthClass to Status.Free,
                            Time.SixthClass to Status.Free
                        ), "some addition 11:00-13:00"
                    )
                )
            )
        )
    }

    override fun bookCabinet(): Completable {
        return Completable.complete()
    }
}