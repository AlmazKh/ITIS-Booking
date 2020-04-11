package com.almaz.itis_booking.data

import com.almaz.itis_booking.core.interfaces.TimetableRepository
import com.almaz.itis_booking.core.model.*
import com.almaz.itis_booking.core.model.remote.BusinessRemote
import com.almaz.itis_booking.core.model.remote.FreeTimeRemote
import com.almaz.itis_booking.core.model.remote.TimetableRemote
import com.almaz.itis_booking.core.model.remote.UserRemote
import com.almaz.itis_booking.data.api.BookingApi
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class TimetableRepositoryImpl
@Inject constructor(
    private val bookingApi: BookingApi
) : TimetableRepository {

    private var queryDate: String = ""

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

    override fun getCabinetsFreeTime(date: String): Single<List<Cabinet>> {
        queryDate = date
        return Single.fromObservable(
            bookingApi.getCabinetFreeTimeByDate("11/04/2020")
        )
            .map {
                mapFreeTimeToCabinet(it)
            }
    }

    override fun bookCabinet(): Completable {
        return Completable.complete()
    }

    private fun mapFreeTimeToCabinet(remote: List<FreeTimeRemote>): List<Cabinet> {
        val list: MutableList<Cabinet> = mutableListOf()
        val set: MutableSet<Int> = mutableSetOf()

        for(elt in remote) {
            if (set.contains(elt.cabinet.id)) {
                continue
            }
            list.add(Cabinet(
                elt.cabinet.id,
                elt.cabinet.number,
                elt.cabinet.capacity,
                null,
                getCabinetBusinessFreeTimeList(elt.cabinet.id, remote)
            ))
            set.add(elt.cabinet.id)
        }
        return list
    }

    private fun getCabinetBusinessFreeTimeList(
        cabinetId: Int,
        list: List<FreeTimeRemote>
    ): List<Business> {
        return list.filter { it.cabinet.id == cabinetId }
            .map {
                Business(
                    it.id,
                    queryDate,
                    Time.valueOf(it.time),
                    Status.Free,
                    null
                )
            }
    }

    private fun mapRemoteTimetableToLocal(remote: TimetableRemote): Timetable {
        val list: MutableList<Cabinet> = mutableListOf()
        val set: MutableSet<Int> = mutableSetOf()
        for (business: BusinessRemote in remote.businesses!!) {
            if (set.contains(business.cabinet.id)) {
                continue
            }
            list.add(
                Cabinet(
                    business.cabinet.id,
                    business.cabinet.number.toString(),
                    business.cabinet.capacity.toString(),
                    "свободная аудитория ... будте инфа тут",
                    addFreeCabinets(
                        getCabinetBusinessList(
                            business.cabinet.id,
                            remote.businesses.toMutableList()
                        )
                    )
                )
            )
            set.add(business.cabinet.id)
        }
        return Timetable(list)
    }

    private fun getCabinetBusinessList(
        cabinetId: Int,
        list: MutableList<BusinessRemote>
    ): MutableList<Business> {
        return list.filter { it.cabinet.id == cabinetId }
            .map {
                Business(
                    it.id,
                    it.date,
                    Time.valueOf(it.time),
                    Status.valueOf(it.status),
                    mapRemoteUserToLocal(it.usr)
                )
            }.toMutableList()
    }

    private fun addFreeCabinets(list: MutableList<Business>): MutableList<Business> {
        Time.values().forEach {
            if (!list.contains<Any>(it)) {
                list.add(
                    Business(
                        null,
                        list[0].date,
                        it,
                        Status.Free,
                        null
                        )
                )
            }
        }
        return list
    }

    private fun mapRemoteUserToLocal(remote: UserRemote): User {
        return User(
            remote.id,
            remote.name,
            remote.institute,
            remote.groupNumber,
            remote.priority.toString(),
            remote.email,
            null
        )
    }

    private fun createAddition(map: Map<Time, Status>): String {
        for (elt in map) {
            if (elt.value == Status.Free) {
                return "Аудитория свободна в ${elt.key.getStringTime()}"
            }
        }
        return "На сегодня все занято"
    }
}
