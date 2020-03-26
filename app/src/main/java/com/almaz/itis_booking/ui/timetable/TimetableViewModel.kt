package com.almaz.itis_booking.ui.timetable

import androidx.lifecycle.MutableLiveData
import com.almaz.itis_booking.core.interactors.TimetableInteractor
import com.almaz.itis_booking.core.model.Cabinet
import com.almaz.itis_booking.core.model.Timetable
import com.almaz.itis_booking.ui.base.BaseViewModel
import com.almaz.itis_booking.utils.Response
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject

class TimetableViewModel
@Inject constructor(
    private val timetableInteractor: TimetableInteractor
) : BaseViewModel() {

    val timetableLiveData = MutableLiveData<Response<Timetable>>()
    val cabinetClickLiveData = MutableLiveData<Response<Cabinet>>()

    fun updateTimetable() {
        showLoadingLiveData.value = true
        disposables.add(
            timetableInteractor.getTimetable()
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate{
                    showLoadingLiveData.value = false
                }
                .subscribe({
                    timetableLiveData.value = Response.success(it)
                }, { error ->
                    timetableLiveData.value = Response.error(error)
                    error.printStackTrace()
                })
        )
    }

    fun onCabinetClick(cabinet: Cabinet) {
        cabinetClickLiveData.value = Response.success(cabinet)
    }
}
