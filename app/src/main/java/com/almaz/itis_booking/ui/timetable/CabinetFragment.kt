package com.almaz.itis_booking.ui.timetable

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import com.almaz.itis_booking.App
import com.almaz.itis_booking.R
import com.almaz.itis_booking.core.model.Cabinet
import com.almaz.itis_booking.core.model.Status
import com.almaz.itis_booking.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_cabinet.*

class CabinetFragment : BaseFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.appComponent
            .timetableComponent()
            .withActivity(activity as AppCompatActivity)
            .build()
            .inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setArrowToolbarVisibility(true)

        requireActivity().onBackPressedDispatcher.addCallback(
            this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    rootActivity.navController.navigateUp()
                    setArrowToolbarVisibility(false)
                }
            })

        return inflater.inflate(R.layout.fragment_cabinet, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tv_cabinet_number.text = arguments?.getParcelable<Cabinet>("cabinet")?.number
        tv_cabinet_capacity_value.text = arguments?.getParcelable<Cabinet>("cabinet")?.capacity
        tv_cabinet_status_addition.text =
            arguments?.getParcelable<Cabinet>("cabinet")?.statusAddition
        when (arguments?.getParcelable<Cabinet>("cabinet")?.status) {
            Status.Free -> {
                iv_cabinet_status.background = rootActivity.resources
                    .getDrawable(R.drawable.cabinet_status_free, null)
            }
            Status.Booked -> {
                iv_cabinet_status.background = rootActivity.resources
                    .getDrawable(R.drawable.cabinet_status_booked, null)
            }
        }
    }

    companion object {
        fun newInstance(arguments: Bundle?): CabinetFragment {
            val fragment = CabinetFragment()
            fragment.arguments = arguments
            return fragment
        }
    }
}
