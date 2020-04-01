package com.almaz.itis_booking.ui.timetable

import androidx.lifecycle.MutableLiveData
import com.almaz.itis_booking.core.interactors.TimetableInteractor
import com.almaz.itis_booking.ui.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject

class CabinetViewModel
@Inject constructor(
    private val timetableInteractor: TimetableInteractor
) : BaseViewModel() {

    val cabinetBookedLiveData = MutableLiveData<Boolean>()

    fun bookCabinet() {
        disposables.addAll(
            timetableInteractor.bookCabinet()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    cabinetBookedLiveData.value = true
                }, {
                    cabinetBookedLiveData.value = false
                    it.printStackTrace()
                })
        )
    }
}