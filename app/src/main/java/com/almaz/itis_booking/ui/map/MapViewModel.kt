package com.almaz.itis_booking.ui.map

import androidx.lifecycle.MutableLiveData
import com.almaz.itis_booking.core.interactors.MapInteractor
import com.almaz.itis_booking.core.model.Cabinet
import com.almaz.itis_booking.ui.base.BaseViewModel
import com.almaz.itis_booking.utils.Response
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject

class MapViewModel
@Inject constructor(
    private val mapInteractor: MapInteractor
) : BaseViewModel() {

    val filteredMapLiveData = MutableLiveData<Response<List<String>?>>()

    fun getData(
        date: String,
        time: String,
        floor: String
    ) {
        showLoadingLiveData.value = true
        disposables.add(
            mapInteractor.getDataForMapWithFilter(
                date,
                time,
                floor
            )
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate {
                    showLoadingLiveData.value = false
                }
                .subscribe(
                    {
                        filteredMapLiveData.value = Response.success(it)
                    },
                    { error ->
                        filteredMapLiveData.value = Response.error(error)
                        error.printStackTrace()
                    })
        )
    }
}