package com.almaz.itis_booking.ui.timetable

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.almaz.itis_booking.App
import com.almaz.itis_booking.R
import com.almaz.itis_booking.ui.base.BaseFragment
import com.almaz.itis_booking.utils.ViewModelFactory
import kotlinx.android.synthetic.main.fragment_timetable.*
import javax.inject.Inject

class TimetableFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: TimetableViewModel
    private lateinit var timetableAdapter: TimetableAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.appComponent
            .timetableComponent()
            .withActivity(activity as AppCompatActivity)
            .build()
            .inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_timetable, container, false)
        setHasOptionsMenu(true)
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        swipe_container.setOnRefreshListener {
            refreshTimetable()
        }
        rv_timetable.apply {
            layoutManager = LinearLayoutManager(rootView.context)
        }
        viewModel = ViewModelProvider(this, this.viewModelFactory)
            .get(TimetableViewModel::class.java)

        setToolbarAndBottomNavVisibility(
            toolbarVisibility = View.VISIBLE,
            bottomNavVisibility = View.VISIBLE
        )
        setArrowToolbarVisibility(false)
        setToolbarLogoVisibility(View.VISIBLE)
        setToolbarTitle("Booking")

        initAdapter()

        observeShowLoadingLiveData()
        observeTimetableLiveData()
        observeCabinetClickLiveData()
    }

    private fun initAdapter() {
        timetableAdapter = TimetableAdapter {
            viewModel.onCabinetClick(it)
        }
        rv_timetable.adapter = timetableAdapter
        viewModel.updateTimetable()
    }

    private fun refreshTimetable() {
        viewModel.updateTimetable()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.with_filter_toolbar, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.filter -> {
                rootActivity.navController.navigate(R.id.action_timetableFragment_to_filterFragment)
                showSnackbar("U go to filter SUCCESS")
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun observeTimetableLiveData() =
        viewModel.timetableLiveData.observe(viewLifecycleOwner, Observer {
            it?.let {
                if (it.data != null) {
                    timetableAdapter.submitList(it.data.cabinets)
                    rv_timetable.adapter = timetableAdapter
                }
                if (it.error != null) {
                    showSnackbar(getString(R.string.snackbar_error_message))
                }
            }
        })

    private fun observeCabinetClickLiveData() =
        viewModel.cabinetClickLiveData.observe(viewLifecycleOwner, Observer {
            it?.let {
                if (it.data != null) {
                    rootActivity.navController.navigate(
                        R.id.action_timetableFragment_to_cabinetFragment,
                        bundleOf("cabinet" to it.data)
                    )
                }
                if (it.error != null) {
                    showSnackbar(getString(R.string.snackbar_error_message))
                }
            }
        })

    private fun observeShowLoadingLiveData() =
        viewModel.showLoadingLiveData.observe(viewLifecycleOwner, Observer {
            it?.let { show ->
                rootActivity.showLoading(show)
            }
        })

    companion object {
        fun newInstance() = TimetableFragment()
    }
}
