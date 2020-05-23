package com.almaz.itis_booking.ui.timetable

import androidx.lifecycle.MutableLiveData
import com.almaz.itis_booking.core.interactors.TimetableInteractor
import com.almaz.itis_booking.core.model.Cabinet
import com.almaz.itis_booking.ui.base.BaseViewModel
import com.almaz.itis_booking.utils.FilterStateData
import com.almaz.itis_booking.utils.Response
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject

class TimetableViewModel
@Inject constructor(
    private val timetableInteractor: TimetableInteractor
) : BaseViewModel() {

    enum class FilterState {
        ACTIVATED, DISABLED
    }

    val filterStateData = MutableLiveData<FilterStateData>()
    val filterState = MutableLiveData<FilterState>()
    val timetableLiveData = MutableLiveData<Response<List<Cabinet>>>()
    val cabinetClickLiveData = MutableLiveData<Response<Cabinet>>()

    init {
        filterState.value = FilterState.DISABLED
    }

    fun updateTimetable(date: String) {
        showLoadingLiveData.value = true
        disposables.add(
            timetableInteractor.getCabinetsFreeTime(date)
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
                        timetableLiveData.value = Response.success(it)
                    },
                    { error ->
                        timetableLiveData.value = Response.error(error)
                        error.printStackTrace()
                    })
        )
    }

    fun updateFilterState(state: FilterState, data: FilterStateData?) {
        when (state) {
            FilterState.DISABLED -> {
                filterStateData.value = null
            }
            FilterState.ACTIVATED -> {
                filterStateData.value = data
            }
        }
        filterState.value = state
    }
}
