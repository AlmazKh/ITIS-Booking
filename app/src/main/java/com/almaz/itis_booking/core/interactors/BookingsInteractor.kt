package com.almaz.itis_booking.core.interactors

import com.almaz.itis_booking.core.interfaces.UserRepository
import com.almaz.itis_booking.core.model.Business
import com.almaz.itis_booking.core.model.Cabinet
import com.almaz.itis_booking.core.model.User
import com.almaz.itis_booking.core.model.remote.BusinessRemote
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class BookingsInteractor
@Inject constructor(
    private val userRepository: UserRepository
){

    fun getUserBookings(): Single<List<BusinessRemote>> =
        userRepository.getCurrentUser()
            .flatMap {
                userRepository.getUserBookings(it.id)
            }
            .subscribeOn(Schedulers.io())
}
