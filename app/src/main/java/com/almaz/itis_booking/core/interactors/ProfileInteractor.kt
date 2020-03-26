package com.almaz.itis_booking.core.interactors

import com.almaz.itis_booking.core.interfaces.UserRepository
import com.almaz.itis_booking.core.model.User
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ProfileInteractor
@Inject constructor(
    private val userRepository: UserRepository
){

    fun getUserInfo(): Single<User> =
        userRepository.getCurrentUser()
            .subscribeOn(Schedulers.io())
}