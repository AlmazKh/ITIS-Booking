package com.almaz.itis_booking.core.interactors

import com.almaz.itis_booking.core.interfaces.UserRepository
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MainInteractor
@Inject constructor(
    private val userRepository: UserRepository
) {
    fun checkAuthUser(): Single<Boolean> =
        userRepository.checkAuthUser()
            .subscribeOn(Schedulers.io())
}