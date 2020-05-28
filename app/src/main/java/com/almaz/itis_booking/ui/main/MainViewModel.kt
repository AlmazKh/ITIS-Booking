package com.almaz.itis_booking.ui.main

import androidx.lifecycle.MutableLiveData
import com.almaz.itis_booking.core.interactors.MainInteractor
import com.almaz.itis_booking.ui.base.BaseViewModel
import com.almaz.itis_booking.utils.AuthenticationState
import com.almaz.itis_booking.utils.Response
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject

class MainViewModel
@Inject constructor(
    private val mainInteractor: MainInteractor
) : BaseViewModel() {

    val authenticationState  = MutableLiveData<AuthenticationState>()

    init {
        checkAuthUser()
    }

    fun checkAuthUser() {
        disposables.add(
            mainInteractor.checkAuthUser()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it) {
                        authenticationState.value = AuthenticationState.AUTHENTICATED
                    } else {
                        authenticationState.value = AuthenticationState.UNAUTHENTICATED
                    }
                }, {
                    it.printStackTrace()
                    authenticationState.value = AuthenticationState.INVALID_AUTHENTICATION
                })
        )
    }

    fun logout() {
        disposables.add(
            mainInteractor.logout()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    authenticationState.value = AuthenticationState.UNAUTHENTICATED
                }, {
                    authenticationState.value = AuthenticationState.INVALID_AUTHENTICATION
                    it.printStackTrace()
                })
        )
    }
}
