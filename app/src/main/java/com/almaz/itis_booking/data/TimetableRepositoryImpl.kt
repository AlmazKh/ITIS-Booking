package com.almaz.itis_booking.data

import com.almaz.itis_booking.core.interfaces.TimetableRepository
import com.almaz.itis_booking.core.model.Cabinet
import com.almaz.itis_booking.core.model.Status
import com.almaz.itis_booking.core.model.Timetable
import io.reactivex.Single
import javax.inject.Inject

class TimetableRepositoryImpl
@Inject constructor() : TimetableRepository {

    override fun getTimetable(): Single<Timetable> {
        return Single.just(
            Timetable(
                listOf(
                    Cabinet(1, "1301", "20", Status.Free, "some addition 10:10-12:00"),
                    Cabinet(2, "1302", "30", Status.Booked, "some addition 11:00-13:00")
                )
            )
        )
    }
}