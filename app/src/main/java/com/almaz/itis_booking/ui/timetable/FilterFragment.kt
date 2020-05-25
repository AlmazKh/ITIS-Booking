package com.almaz.itis_booking.ui.timetable

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.*
import android.widget.DatePicker
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.almaz.itis_booking.App
import com.almaz.itis_booking.R
import com.almaz.itis_booking.ui.base.BaseFragment
import com.almaz.itis_booking.utils.FilterStateData
import com.almaz.itis_booking.utils.ViewModelFactory
import kotlinx.android.synthetic.main.fragment_filter.*
import java.util.*
import javax.inject.Inject


class FilterFragment : BaseFragment(), DatePickerDialog.OnDateSetListener {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: TimetableViewModel

    private var date: String = ""
    private var time = mutableListOf<String>()
    private var bookedStatus: String = ""
    private var floor = mutableListOf<String>()
    private var capacity: String = ""

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
        viewModel = ViewModelProvider(rootActivity, this.viewModelFactory)
            .get(TimetableViewModel::class.java)

        return inflater.inflate(R.layout.fragment_filter, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        setToolbarAndBottomNavVisibility(
                toolbarVisibility = View.VISIBLE,
                bottomNavVisibility = View.GONE
        )
        setToolbarTitle("Фильтр")
        setToolbarLogoVisibility(logoVisibility = View.GONE)

        tv_choose_date.setOnClickListener {
            showDatePickerDialog()
        }

        observeFilterState()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.filter_toolbar, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.btn_add_filter_approve -> {
                makeQuery()
                showSnackbar("Фильтр применен")
                viewModel.updateFilterState(
                    TimetableViewModel.FilterState.ACTIVATED,
                    FilterStateData(
                        date = date,
                        time = time,
                        bookedStatus = bookedStatus,
                        floor = floor,
                        capacity = capacity
                    )
                )
                rootActivity.navController.navigate(
                    R.id.action_filterFragment_to_timetableFragment
                )
                true
            }
            R.id.btn_clear_filter -> {
                showSnackbar("Фильтр успешно сброшен")
                viewModel.updateFilterState(TimetableViewModel.FilterState.DISABLED, null)
                rootActivity.navController.navigateUp()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun observeFilterState() =
        viewModel.filterStateData.observe(viewLifecycleOwner, Observer { it ->
            it?.let { bundle ->
                tv_choose_date.text = bundle.date
                bundle.time.let {
                    ch_time_first.isChecked = it.contains(ch_time_first.text)
                    ch_time_second.isChecked = it.contains(ch_time_second.text)
                    ch_time_third.isChecked = it.contains(ch_time_third.text)
                    ch_time_fourth.isChecked = it.contains(ch_time_fourth.text)
                    ch_time_fifth.isChecked = it.contains(ch_time_fifth.text)
                    ch_time_sixth.isChecked = it.contains(ch_time_sixth.text)
                }
                bundle.bookedStatus.let {
                    if (it == SEARCH_WITH_BOOKED) {
                        ch_free.isChecked = false
                        ch_booked_priority_lower.isChecked = true
                    } else {
                        ch_free.isChecked = true
                        ch_booked_priority_lower.isChecked = false
                    }
                }
                bundle.floor.let {
                    ch_floor_13.isChecked = it.contains(ch_floor_13.text)
                    ch_floor_14.isChecked = it.contains(ch_floor_14.text)
                    ch_floor_15.isChecked = it.contains(ch_floor_15.text)
                }
                bundle.capacity.let {
                    when (it) {
                        "20" -> {
                            ch_low_20.isChecked = true
                            ch_between_20_30.isChecked = false
                            ch_over_30.isChecked = false
                        }
                        "30" -> {
                            ch_low_20.isChecked = true
                            ch_between_20_30.isChecked = false
                            ch_over_30.isChecked = false
                        }
                        else -> {
                            ch_low_20.isChecked = false
                            ch_between_20_30.isChecked = false
                            ch_over_30.isChecked = true
                        }
                    }
                }
            }
        })

    private fun showDatePickerDialog() {
        val datePickerDialog = DatePickerDialog(
                rootActivity,
                this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH))
        datePickerDialog.show()
        datePickerDialog.getButton(DatePickerDialog.BUTTON_NEGATIVE)
                .setTextColor(rootActivity.resources.getColor(R.color.colorPrimaryDark))
        datePickerDialog.getButton(DatePickerDialog.BUTTON_POSITIVE)
                .setTextColor(rootActivity.resources.getColor(R.color.colorPrimaryDark))
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        when(month) {
            9 - 11 -> tv_choose_date.text = "$dayOfMonth.${month + 1}.$year"
            else -> tv_choose_date.text = "$dayOfMonth.0${month + 1}.$year"
        }

    }

    private fun makeQuery() {
        date = tv_choose_date.text.toString()
        time = mutableListOf()
        if (ch_time_first.isChecked) time.add(ch_time_first.text.toString())
        if (ch_time_second.isChecked) time.add(ch_time_second.text.toString())
        if (ch_time_third.isChecked) time.add(ch_time_third.text.toString())
        if (ch_time_fourth.isChecked) time.add(ch_time_fourth.text.toString())
        if (ch_time_fifth.isChecked) time.add(ch_time_fifth.text.toString())
        if (ch_time_sixth.isChecked) time.add(ch_time_sixth.text.toString())
        // filtering mode by booked status
        bookedStatus =
            if (ch_booked_priority_lower.isChecked) SEARCH_WITH_BOOKED else SEARCH_JUST_FREE
        floor = mutableListOf()
        if (ch_floor_13.isChecked) floor.add(ch_floor_13.text.toString())
        if (ch_floor_14.isChecked) floor.add(ch_floor_14.text.toString())
        if (ch_floor_15.isChecked) floor.add(ch_floor_15.text.toString())
        capacity = when {
            ch_low_20.isChecked -> "20"
            ch_between_20_30.isChecked -> "30"
            else -> "30"
        }
        viewModel.getData(date, time, bookedStatus, floor, capacity)
    }

    companion object {
        const val SEARCH_JUST_FREE = "FREE"
        const val SEARCH_WITH_BOOKED = "ALL"
    }
}