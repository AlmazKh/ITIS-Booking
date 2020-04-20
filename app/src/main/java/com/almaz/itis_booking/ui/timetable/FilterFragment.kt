package com.almaz.itis_booking.ui.timetable

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.*
import android.widget.DatePicker
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.almaz.itis_booking.App
import com.almaz.itis_booking.R
import com.almaz.itis_booking.ui.base.BaseFragment
import com.almaz.itis_booking.utils.ViewModelFactory
import kotlinx.android.synthetic.main.fragment_filter.*
import java.util.*
import javax.inject.Inject


class FilterFragment : BaseFragment(), DatePickerDialog.OnDateSetListener {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: FilterViewModel

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
                .get(FilterViewModel::class.java)

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

        setUpData()
        tv_choose_date.setOnClickListener {
            showDatePickerDialog()
        }

        observeFilteredTimetableLiveData()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.filter_toolbar, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.btn_add_filter_approve -> {
                makeQuery()
                showSnackbar("Approveeee")
                true
            }
            R.id.btn_clear_filter -> {
                setUpDefaultData()
                showSnackbar("Clear successfully")
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

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
        tv_choose_date.text = "$dayOfMonth/${month + 1}/$year"
    }

    private fun makeQuery() {
        val time = mutableListOf<String>()
        if (ch_time_first.isChecked) time.add(ch_time_first.text.toString())
        if (ch_time_second.isChecked) time.add(ch_time_second.text.toString())
        if (ch_time_third.isChecked) time.add(ch_time_third.text.toString())
        if (ch_time_fourth.isChecked) time.add(ch_time_fourth.text.toString())
        if (ch_time_fifth.isChecked) time.add(ch_time_fifth.text.toString())
        if (ch_time_sixth.isChecked) time.add(ch_time_sixth.text.toString())
        val bookedStatus =
            if (ch_booked_priority_lower.isChecked) SEARCH_WITH_BOOKED else SEARCH_JUST_FREE
        val floor = mutableListOf<String>()
        if (ch_floor_13.isChecked) floor.add(ch_floor_13.text.toString())
        if (ch_floor_14.isChecked) floor.add(ch_floor_14.text.toString())
        if (ch_floor_15.isChecked) floor.add(ch_floor_15.text.toString())
        val capacity = when {
            ch_low_20.isChecked -> 20.toString()
            ch_between_20_30.isChecked -> 30.toString()
            else -> ch_over_30.text.toString()
        }
//        viewModel.getData(tv_choose_date.text.toString(), time, bookedStatus, floor, capacity)
        viewModel.getData("11/04/2020", time, bookedStatus, floor, capacity)

    }

    private fun observeFilteredTimetableLiveData() =
        viewModel.filteredTimetableLiveData.observe(viewLifecycleOwner, Observer {
            it.let {
                if (it.data != null) {
                    rootActivity.navController.navigate(
                        R.id.action_filterFragment_to_timetableFragment,
                        bundleOf("cabinets" to it.data)
                    )
                }
                if (it.error != null) {
                    showSnackbar(getString(R.string.snackbar_error_message))
                }
                if (it.data == null) {
                    showSnackbar("К сожалению, нет свободных аудиторий")
                }
            }
        })

    private fun setUpData() {

    }

    private fun setUpDefaultData() {

    }

    companion object {
        const val SEARCH_JUST_FREE = "FREE"
        const val SEARCH_WITH_BOOKED = "ALL"
    }
}