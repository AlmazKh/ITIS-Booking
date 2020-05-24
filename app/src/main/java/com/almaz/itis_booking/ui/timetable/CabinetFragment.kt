package com.almaz.itis_booking.ui.timetable

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.almaz.itis_booking.App
import com.almaz.itis_booking.R
import com.almaz.itis_booking.core.model.Business
import com.almaz.itis_booking.core.model.Cabinet
import com.almaz.itis_booking.core.model.Status
import com.almaz.itis_booking.core.model.Time
import com.almaz.itis_booking.ui.base.BaseFragment
import com.almaz.itis_booking.utils.ViewModelFactory
import kotlinx.android.synthetic.main.fragment_cabinet.*
import javax.inject.Inject

class CabinetFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: CabinetViewModel

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
        viewModel = ViewModelProvider(this, this.viewModelFactory)
            .get(CabinetViewModel::class.java)

        return inflater.inflate(R.layout.fragment_cabinet, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setToolbarAndBottomNavVisibility(
            toolbarVisibility = View.VISIBLE,
            bottomNavVisibility = View.GONE
        )
        setArrowToolbarVisibility(View.VISIBLE)
        setToolbarTitle("Бронирование")
        setToolbarLogoVisibility(logoVisibility = View.GONE)

        setUpData()

        observeCabinetBookedLiveData()

        btn_booking.setOnClickListener {
            arguments?.getParcelable<Cabinet>("cabinet")?.let { it1 ->
                viewModel.bookCabinet(
                    it1,
                    spinner_cabinet_free_time.selectedItem as String
                )
            }
        }

        spinner_cabinet_free_time.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {}

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                        parent?.getItemAtPosition(position).toString()

                }
            }
    }

    private fun setUpData() {
        tv_cabinet_number.text = arguments?.getParcelable<Cabinet>("cabinet")?.number
        tv_cabinet_capacity_value.text = arguments?.getParcelable<Cabinet>("cabinet")?.capacity
        tv_cabinet_status_addition.text =
            arguments?.getParcelable<Cabinet>("cabinet")?.statusAddition

        val business =
            mapCabinetBusinessIntoStringTime(arguments?.getParcelable<Cabinet>("cabinet")?.business)
        if (business != null) {
            setUpDataIntoSpinner(business)
        } else {
            spinner_cabinet_free_time.visibility = View.GONE
            btn_booking.isActivated = false
            showSnackbar("No free time")
        }
    }

    private fun mapCabinetBusinessIntoStringTime(business: List<Business>?): MutableList<String>? {
        val list: MutableList<String>? = mutableListOf()
        if (business != null) {
            for (item in business) {
                if (item.status == Status.Free) {
                    when (item.time) {
                        Time.FirstClass -> list?.add(Time.FirstClass.getStringTime())
                        Time.SecondClass -> list?.add(Time.SecondClass.getStringTime())
                        Time.ThirdClass -> list?.add(Time.ThirdClass.getStringTime())
                        Time.FourthClass -> list?.add(Time.FourthClass.getStringTime())
                        Time.FifthClass -> list?.add(Time.FifthClass.getStringTime())
                        Time.SixthClass -> list?.add(Time.SixthClass.getStringTime())
                    }
                }
            }
        }
        return list
    }

    private fun setUpDataIntoSpinner(list: List<String>) {
        val adapter = context?.let { ArrayAdapter(it, android.R.layout.simple_spinner_item, list) }
        adapter?.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner_cabinet_free_time.adapter = adapter
    }

    /*private fun setUpStatus(time: Time) {
        when (arguments?.getParcelable<Cabinet>("cabinet")?.business?.get(time)) {
            Status.Free -> {
                iv_cabinet_status.background = rootActivity.resources
                    .getDrawable(R.drawable.cabinet_status_free, null)
            }
            Status.Booked -> {
                iv_cabinet_status.background = rootActivity.resources
                    .getDrawable(R.drawable.cabinet_status_booked, null)

            }
        }
    }*/

    private fun observeCabinetBookedLiveData() =
        viewModel.cabinetBookedLiveData.observe(viewLifecycleOwner, Observer {
            it?.let {
                if (it) {
                    showSnackbar("Booked")
                    rootActivity.navController.navigate(R.id.action_cabinetFragment_to_timetableFragment)
                } else {
                    showSnackbar(R.string.snackbar_error_message.toString())
                }
            }
        })

    override fun onDestroy() {
        super.onDestroy()
        setArrowToolbarVisibility(View.GONE)
    }
}
