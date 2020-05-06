package com.almaz.itis_booking.ui.map

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.almaz.itis_booking.App
import com.almaz.itis_booking.R
import com.almaz.itis_booking.core.model.Cabinet
import com.almaz.itis_booking.ui.base.BaseFragment
import com.almaz.itis_booking.utils.ViewModelFactory
import kotlinx.android.synthetic.main.floor_13.*
import kotlinx.android.synthetic.main.floor_14.*
import kotlinx.android.synthetic.main.floor_15.*
import javax.inject.Inject

class FloorMap : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: MapViewModel

    private var floor = 13

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.appComponent
            .mapComponent()
            .withActivity(activity as AppCompatActivity)
            .build()
            .inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        arguments?.takeIf { it.containsKey(ARG_OBJECT) }?.apply {
            when (getInt(ARG_OBJECT)) {
                0 -> {
                    floor = 13
                    return inflater.inflate(R.layout.floor_13, container, false)
                }
                1 -> {
                    floor = 14
                    return inflater.inflate(R.layout.floor_14, container, false)
                }
                2 -> {
                    floor = 15
                    return inflater.inflate(R.layout.floor_15, container, false)
                }
            }
        }
        return inflater.inflate(R.layout.floor_13, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(rootActivity, this.viewModelFactory)
            .get(MapViewModel::class.java)

        observeFilteredMapLiveData()
    }

    private fun observeFilteredMapLiveData() =
        viewModel.filteredMapLiveData.observe(viewLifecycleOwner, Observer {
            it?.let {
                if (it.data != null) {
                    setUpCabinetsStatus(it.data)
                }
                if (it.error != null) {
                    showSnackbar(getString(R.string.snackbar_error_message))
                }
            }
        })

    private fun setUpCabinetsStatus(list: List<String>) {
        when (floor) {
            13 -> {
                tv_1301.background = getStatusFromList(list, tv_1301.text.toString())
                tv_1302.background = getStatusFromList(list, tv_1302.text.toString())
                tv_1303.background = getStatusFromList(list, tv_1303.text.toString())
                tv_1304.background = getStatusFromList(list, tv_1304.text.toString())
                tv_1305.background = getStatusFromList(list, tv_1305.text.toString())
                tv_1306.background = getStatusFromList(list, tv_1306.text.toString())
                tv_1307.background = getStatusFromList(list, tv_1307.text.toString())
                tv_1308.background = getStatusFromList(list, tv_1308.text.toString())
                tv_1309.background = getStatusFromList(list, tv_1309.text.toString())
                tv_1310.background = getStatusFromList(list, tv_1310.text.toString())
                tv_1311.background = getStatusFromList(list, tv_1311.text.toString())

            }
            14 -> {
                tv_1404.background = getStatusFromList(list, tv_1404.text.toString())
                tv_1405.background = getStatusFromList(list, tv_1405.text.toString())
                tv_1408.background = getStatusFromList(list, tv_1408.text.toString())
                tv_1409.background = getStatusFromList(list, tv_1409.text.toString())
                tv_1412.background = getStatusFromList(list, tv_1412.text.toString())
            }
            15 -> {
                tv_1508.background = getStatusFromList(list, tv_1508.text.toString())
                tv_1509.background = getStatusFromList(list, tv_1509.text.toString())
            }
        }
    }

    private fun getStatusFromList(list: List<String>, number: String): Drawable {
        return if (list.contains(number)) {
            resources.getDrawable(R.drawable.map_cabinet_booked_background, null)
        } else {
            resources.getDrawable(R.drawable.map_cabinet_free_background, null)
        }
    }
}
