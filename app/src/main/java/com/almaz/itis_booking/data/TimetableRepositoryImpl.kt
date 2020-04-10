package com.almaz.itis_booking.data

import com.almaz.itis_booking.core.interfaces.TimetableRepository
import com.almaz.itis_booking.core.model.Cabinet
import com.almaz.itis_booking.core.model.Status
import com.almaz.itis_booking.core.model.Time
import com.almaz.itis_booking.core.model.Timetable
import com.almaz.itis_booking.core.model.remote.CabinetRemote
import com.almaz.itis_booking.core.model.remote.TimeRemote
import com.almaz.itis_booking.core.model.remote.TimetableRemote
import com.almaz.itis_booking.data.api.BookingApi
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class TimetableRepositoryImpl
@Inject constructor(
    private val bookingApi: BookingApi
) : TimetableRepository {

    /*override fun getTimetable(): Single<Timetable> {
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
    }*/

    override fun getTimetable(): Single<Timetable> {
        return Single.fromObservable(
            bookingApi.getTimetable()
        ).map {
            mapRemoteTimetableToLocal(it)
        }
    }

    override fun bookCabinet(): Completable {
        return Completable.complete()
    }

    private fun mapRemoteTimetableToLocal(remote: TimetableRemote): Timetable {
        val list: MutableList<Cabinet> = mutableListOf()
        for (cabinet: CabinetRemote in remote.cabinetRemotes!!) {
            list.add(mapRemoteCabinetToLocal(cabinet))
        }
        return Timetable(list)
    }

    private fun mapRemoteCabinetToLocal(remote: CabinetRemote): Cabinet {
        val business = mapRemoteBusinessToLocal(remote.business)
        return Cabinet(
            remote.id,
            remote.number.toString(),
            remote.capacity.toString(),
            business,
            createAddition(business)
        )
    }


    private fun mapRemoteBusinessToLocal(remote: TimeRemote): Map<Time, Status> {
        val map: MutableMap<Time, Status> = mutableMapOf()

        when (remote.firstClass) {
            "Free" -> map[Time.FirstClass] = Status.Free
            "Booked" -> map[Time.FirstClass] = Status.Booked
        }
        when (remote.secondClass) {
            "Free" -> map[Time.SecondClass] = Status.Free
            "Booked" -> map[Time.SecondClass] = Status.Booked
        }
        when (remote.thirdClass) {
            "Free" -> map[Time.ThirdClass] = Status.Free
            "Booked" -> map[Time.ThirdClass] = Status.Booked
        }
        when (remote.fourthClass) {
            "Free" -> map[Time.FourthClass] = Status.Free
            "Booked" -> map[Time.FourthClass] = Status.Booked
        }
        when (remote.fifthClass) {
            "Free" -> map[Time.FifthClass] = Status.Free
            "Booked" -> map[Time.FifthClass] = Status.Booked
        }
        when (remote.sixthClass) {
            "Free" -> map[Time.SixthClass] = Status.Free
            "Booked" -> map[Time.SixthClass] = Status.Booked
        }
        return map
    }

    private fun createAddition(map: Map<Time, Status>): String {
        for (elt in map) {
            if (elt.value == Status.Free) {
                return "Аудитория свободна в ${elt.key.getStringTime()}"
            }
        }
        return "На сегодня все занято"
    }

    /*private fun getStringTime(time: Time): String =
        when (time) {
            Time.FirstClass -> "8:30 - 10:00"
            Time.SecondClass -> "10:10 - 11:40"
            Time.ThirdClass -> "11:50 - 13:20"
            Time.FourthClass -> "14:00 - 15:30"
            Time.FifthClass -> "15:40 - 17:10"
            Time.SixthClass -> "17:50 - 19:20"
        }*/
}
