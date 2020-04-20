package com.almaz.itis_booking.ui.timetable

import androidx.lifecycle.MutableLiveData
import com.almaz.itis_booking.core.interactors.TimetableInteractor
import com.almaz.itis_booking.core.model.Cabinet
import com.almaz.itis_booking.ui.base.BaseViewModel
import com.almaz.itis_booking.utils.Response
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject

class FilterViewModel
@Inject constructor(
    private val timetableInteractor: TimetableInteractor
) : BaseViewModel() {

    val filteredTimetableLiveData = MutableLiveData<Response<List<Cabinet>?>>()

    fun getData(
        date: String,
        time: List<String>,
        bookedStatus: String,
        floor: List<String>,
        capacity: String
    ) {
        showLoadingLiveData.value = true
        disposables.add(
            timetableInteractor.getDataWithFilter(
                date,
                time,
                bookedStatus,
                floor,
                capacity
            )
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate {
                    showLoadingLiveData.value = false
                }
                .subscribe(
                    {
                        filteredTimetableLiveData.value = Response.success(it)
                    },
                    { error ->
                        filteredTimetableLiveData.value = Response.error(error)
                        error.printStackTrace()
                    })
        )
    }
}
