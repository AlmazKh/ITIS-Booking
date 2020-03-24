package com.almaz.itis_booking.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.almaz.itis_booking.R
import com.almaz.itis_booking.ui.base.BaseFragment
import com.almaz.itis_booking.ui.map.MapFragment
import kotlinx.android.synthetic.main.activity_main.*

class ProfileFragment : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        setToolbarAndBottomNavVisibility(
                toolbarVisibility = View.VISIBLE,
                bottomNavVisibility = View.VISIBLE
        )
//        rootActivity.bottom_navigation.selectedItemId = R.id.navigation_profile
    }

    companion object {
        fun newInstance() = ProfileFragment()
    }
}