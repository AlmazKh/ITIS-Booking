package com.almaz.itis_booking.ui.main

import androidx.lifecycle.MutableLiveData
import com.almaz.itis_booking.core.interactors.LoginInteractor
import com.almaz.itis_booking.core.interactors.MainInteractor
import com.almaz.itis_booking.ui.base.BaseViewModel
import com.almaz.itis_booking.utils.Response
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject

class MainViewModel
@Inject constructor(
    private val mainInteractor: MainInteractor
) : BaseViewModel() {

    val isLoginedLiveData = MutableLiveData<Response<Boolean>>()

    fun checkAuthUser() {
        disposables.add(
            mainInteractor.checkAuthUser()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it) {
                        isLoginedLiveData.value = Response.success(true)
                    } else {
                        isLoginedLiveData.value = Response.success(false)
                    }
                }, {
                    it.printStackTrace()
                })
        )
    }
}
