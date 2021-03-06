package com.almaz.itis_booking.data

import com.almaz.itis_booking.core.interfaces.TimetableRepository
import com.almaz.itis_booking.core.model.*
import com.almaz.itis_booking.core.model.remote.BusinessRemote
import com.almaz.itis_booking.core.model.remote.CabinetRemote
import com.almaz.itis_booking.core.model.remote.FreeTimeRemote
import com.almaz.itis_booking.core.model.remote.UserRemote
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class TimetableRepositoryImpl
@Inject constructor(
    private val bookingApi: BookingApi,
    private val priorityHolder: PriorityHolder
) : TimetableRepository {

    private var queryDate: String = ""

    override fun getCabinetsFreeTime(date: String): Single<List<Cabinet>> {
        queryDate = date
        return Single.fromObservable(
            bookingApi.getCabinetFreeTimeByDate(date)
        )
            .map {
                mapFreeTimeToCabinet(it)
            }
    }

    override fun getDataWithFilter(
        date: String,
        times: List<String>,
        bookedStatus: String,
        floors: List<String>,
        capacity: String,
        priority: Int
    ): Single<List<Cabinet>> {
        return when (bookedStatus) {
            "FREE" -> {
                Single.fromObservable(
                    bookingApi.getCabinetFreeTimeByFilter(
                        date,
                        getTimeEnumNameFromValue(times),
                        floors,
                        capacity
                    )
                ).map {
                    mapFreeTimeToCabinet(it)
                }
            }
            "ALL" -> {
                Single.fromObservable(
                    bookingApi.getCabinetBusinessByFilter(
                        date,
                        getTimeEnumNameFromValue(times),
                        floors,
                        capacity,
                        priority
                    )
                ).map {
                    mapRemoteBusinessToListOfCabinets(it)
                }
            }
            else -> Single.just(listOf())
        }
    }


    override fun bookCabinet(user: User, cabinet: Cabinet, time: String): Completable {
        return Completable.fromObservable(
            bookingApi.bookCabinet(
                BusinessRemote(
                    0,
                    cabinet.business.first().date,
                    getTimeEnumNameFromValue(listOf(time)).first(),
                    Status.Booked.toString(),
                    CabinetRemote(
                        cabinet.id,
                        cabinet.number.toInt(),
                        cabinet.capacity.toInt()
                    ),
                    UserRemote(
                        user.id,
                        user.name,
                        user.institute,
                        user.groupNumber,
                        priorityHolder.getPriorityValueByName(user.priority),
                        user.email!!,
                        user.photo
                    )
                )
            )
        )
    }

    override fun getDataForMapWithFilter(
        date: String,
        time: String,
        floor: String
    ): Single<List<String>> {
        return Single.fromObservable(
            bookingApi.getMapBookingCabinets(
                date,
                getClassTimeWithCurrentTime(time),
                floor
            )
        )
    }

    private fun getClassTimeWithCurrentTime(currentTime: String): String {
        return when (currentTime) {
            "10", "11" -> Time.SecondClass.name
            "12", "13" -> Time.ThirdClass.name
            "14", "15" -> Time.FourthClass.name
            "16", "17" -> Time.FifthClass.name
            "18", "19" -> Time.SixthClass.name
            else -> Time.FirstClass.name
        }
    }

    private fun getTimeEnumNameFromValue(times: List<String>): List<String> {
        val list: MutableList<String> = mutableListOf()
        for (item in Time.values()) {
            for (time in times) {
                if (item.getStringTime() == time) {
                    list.add(item.toString())
                    break
                }
            }
        }
        return list
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
                createAddition(
                    cabinetId = elt.cabinet.id,
                    listFreeTime = remote,
                    listBusiness = null
                ),
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


    private fun mapRemoteBusinessToListOfCabinets(remote: List<BusinessRemote>): List<Cabinet> {
        val list: MutableList<Cabinet> = mutableListOf()
        val set: MutableSet<Int> = mutableSetOf()
        for (business: BusinessRemote in remote) {
            if (set.contains(business.cabinet.id)) {
                continue
            }
            list.add(
                Cabinet(
                    business.cabinet.id,
                    business.cabinet.number.toString(),
                    business.cabinet.capacity.toString(),
                    createAddition(
                        cabinetId = business.cabinet.id,
                        listFreeTime = null,
                        listBusiness = remote
                    ),
                        getCabinetBusinessList(
                            business.cabinet.id,
                            remote.toMutableList()
                    )
                )
            )
            set.add(business.cabinet.id)
        }
        return list
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
                    if (it.usr.id == 0) null else mapRemoteUserToLocal(it.usr)
                )
            }.toMutableList()
    }

    private fun mapRemoteUserToLocal(remote: UserRemote): User {
        return User(
            remote.id,
            remote.name,
            remote.institute,
            remote.groupNumber,
            priorityHolder.getPriorityNameByValue(remote.priority),
            remote.email,
            remote.photo
        )
    }

    private fun createAddition(
        cabinetId: Int,
        listBusiness: List<BusinessRemote>?,
        listFreeTime: List<FreeTimeRemote>?
    ): String {

        listBusiness?.let { list ->
            list.filter { it.cabinet.id == cabinetId }
                .forEach {
                    if (it.status == Status.Free.toString()) {
                        return "Аудитория свободна в ${Time.valueOf(it.time).getStringTime()}"
                    }
                }
        }
        listFreeTime?.let { list ->
            list.filter { it.cabinet.id == cabinetId }
                .forEach {
                    return "Аудитория свободна в ${Time.valueOf(it.time).getStringTime()}"
                }
        }
        return "Аудитория забронирована, но ваш приоритет позволяет перебить бронь"
    }
}
