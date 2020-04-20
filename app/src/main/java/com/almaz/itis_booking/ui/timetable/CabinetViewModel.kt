package com.almaz.itis_booking.ui.timetable

import androidx.lifecycle.MutableLiveData
import com.almaz.itis_booking.core.interactors.TimetableInteractor
import com.almaz.itis_booking.core.model.Cabinet
import com.almaz.itis_booking.ui.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject

class CabinetViewModel
@Inject constructor(
    private val timetableInteractor: TimetableInteractor
) : BaseViewModel() {

    val cabinetBookedLiveData = MutableLiveData<Boolean>()

    fun bookCabinet(cabinet: Cabinet, time: String) {
        disposables.addAll(
            timetableInteractor.bookCabinet(cabinet, time)
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
