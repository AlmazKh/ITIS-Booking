package com.almaz.itis_booking.ui.bookings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.almaz.itis_booking.App
import com.almaz.itis_booking.R
import com.almaz.itis_booking.ui.base.BaseFragment
import com.almaz.itis_booking.utils.ViewModelFactory
import kotlinx.android.synthetic.main.fragment_bookings.*
import javax.inject.Inject

class BookingsListFragment : BaseFragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: BookingsViewModel
    private lateinit var bookingsAdapter: BookingsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.appComponent
            .profileComponent()
            .withActivity(activity as AppCompatActivity)
            .build()
            .inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_bookings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rv_bookings.apply {
            layoutManager = LinearLayoutManager(rootView.context)
        }
        viewModel = ViewModelProvider(this, this.viewModelFactory)
            .get(BookingsViewModel::class.java)

        setToolbarAndBottomNavVisibility(
            toolbarVisibility = View.VISIBLE,
            bottomNavVisibility = View.VISIBLE
        )
        setArrowToolbarVisibility(false)
        setToolbarLogoVisibility(View.VISIBLE)
        setToolbarTitle("Ваши бронирования")

        initAdapter()

        observeBookingsLiveData()
    }

    private fun initAdapter() {
        bookingsAdapter = BookingsAdapter(rootActivity)
        rv_bookings.adapter = bookingsAdapter
        viewModel.updateUserBookings()
    }

    private fun observeBookingsLiveData() =
        viewModel.userBookingsLiveData.observe(viewLifecycleOwner, Observer {
            it?.let {
                if (it.data != null) {
                    bookingsAdapter.submitList(it.data)
                    rv_bookings.adapter = bookingsAdapter
                }
                if (it.error != null) {
                    showSnackbar(getString(R.string.snackbar_error_message))
                }
            }
        })
}