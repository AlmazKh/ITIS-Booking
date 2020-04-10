package com.almaz.itis_booking.ui.timetable

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.*
import android.widget.DatePicker
import androidx.appcompat.app.AppCompatActivity
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
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.filter_toolbar, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.btn_add_filter_approve -> {
                rootActivity.navController.navigateUp()
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
        tv_choose_date.text = "$dayOfMonth / $month / $year"
    }

    private fun setUpData() {

    }

    private fun setUpDefaultData() {

    }

}