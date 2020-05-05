package com.almaz.itis_booking.ui.map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.almaz.itis_booking.R
import com.almaz.itis_booking.ui.base.BaseFragment
import kotlinx.android.synthetic.main.item_floor_map.*

class FloorMap : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        /*arguments?.takeIf { it.containsKey(ARG_OBJECT) }?.apply {
            when (getInt(ARG_OBJECT)) {
                0 -> {
                    return inflater.inflate(R.layout.floor_13, container, false)
                }
                1 -> {
                    return CustomFloor13(rootActivity)
                }
                2 -> {
                    return inflater.inflate(R.layout.floor_13, container, false)
                }
            }
        }*/
        return CustomFloor13(rootActivity)
    }
}
