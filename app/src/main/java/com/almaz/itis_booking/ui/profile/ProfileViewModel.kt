package com.almaz.itis_booking.ui.profile

import androidx.lifecycle.MutableLiveData
import com.almaz.itis_booking.core.interactors.ProfileInteractor
import com.almaz.itis_booking.core.model.User
import com.almaz.itis_booking.ui.base.BaseViewModel
import com.almaz.itis_booking.utils.Response
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ProfileViewModel
@Inject constructor(
    private val profileInteractor: ProfileInteractor
) : BaseViewModel() {

    val userInfoLiveData = MutableLiveData<Response<User>>()

    fun getUserInfo() {
        disposables.addAll(
            profileInteractor.getUserInfo()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    userInfoLiveData.value = Response.success(it)
                }, {
                    userInfoLiveData.value = Response.error(it)
                    it.printStackTrace()
                })
        )
    }
}