package com.almaz.itis_booking.ui.bookings

import androidx.lifecycle.MutableLiveData
import com.almaz.itis_booking.core.interactors.BookingsInteractor
import com.almaz.itis_booking.core.model.remote.BusinessRemote
import com.almaz.itis_booking.ui.base.BaseViewModel
import com.almaz.itis_booking.utils.Response
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject

class BookingsViewModel
@Inject constructor(
    private val bookingsInteractor: BookingsInteractor
) : BaseViewModel() {

    val userBookingsLiveData = MutableLiveData<Response<List<BusinessRemote>>>()
    val cancelBookingLiveData = MutableLiveData<Response<Boolean>>()

    fun updateUserBookings() {
        showLoadingLiveData.value = true
        disposables.add(
            bookingsInteractor.getUserBookings()
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate{
                    showLoadingLiveData.value = false
                }
                .subscribe({
                    userBookingsLiveData.value = Response.success(it)
                }, { error ->
                    userBookingsLiveData.value = Response.error(error)
                    error.printStackTrace()
                })
        )
    }

    fun cancelBooking(bookingId: Int) {
        disposables.add(
            bookingsInteractor.cancelBooking(bookingId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    cancelBookingLiveData.value = Response.success(true)
                }, { error ->
                    cancelBookingLiveData.value = Response.error(error)
                    error.printStackTrace()
                })
        )
    }
}
