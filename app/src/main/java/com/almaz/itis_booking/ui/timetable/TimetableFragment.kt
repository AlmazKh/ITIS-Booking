package com.almaz.itis_booking.ui.timetable

import android.os.Bundle
import android.view.*
import com.almaz.itis_booking.R
import com.almaz.itis_booking.ui.base.BaseFragment
import com.almaz.itis_booking.ui.profile.ProfileFragment

class TimetableFragment : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_timetable, container, false)
        setHasOptionsMenu(true)
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.with_filter_toolbar, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.filter -> {
                showSnackbar("U go to filter SUCCESS")
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun init() {
        /*setToolbarAndBottomNavVisibility(
                toolbarVisibility = View.VISIBLE,
                bottomNavVisibility = View.VISIBLE
        )*/
    }

    companion object {
        fun newInstance() = ProfileFragment()
    }
}