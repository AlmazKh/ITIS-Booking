package com.almaz.itis_booking.ui.map

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.almaz.itis_booking.App
import com.almaz.itis_booking.R
import com.almaz.itis_booking.ui.base.BaseFragment
import com.almaz.itis_booking.utils.ViewModelFactory
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.fragment_map.*
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class MapFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: MapViewModel

    private lateinit var mapViewPagerAdapter: MapViewPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.appComponent
                .mapComponent()
                .withActivity(activity as AppCompatActivity)
                .build()
                .inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_map, container, false)
        setHasOptionsMenu(true)
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mapViewPagerAdapter = MapViewPagerAdapter(this)
        vp_map.adapter = mapViewPagerAdapter

        viewModel = ViewModelProvider(rootActivity, this.viewModelFactory)
            .get(MapViewModel::class.java)

        TabLayoutMediator(tabs_floor, vp_map,
                TabLayoutMediator.TabConfigurationStrategy { tab, position ->
                    when(position) {
                        0 -> tab.text = "13"
                        1 -> tab.text = "14"
                        2 -> tab.text = "15"
                    }
                }).attach()

        viewModel.getData(
            getCurrentDate(),
            getCurrentTime(),
            "13"
        )

        tabs_floor.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {}

            override fun onTabUnselected(tab: TabLayout.Tab?) {}

            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.position) {
                    0 -> {
                        viewModel.getData(
                            getCurrentDate(),
                            getCurrentTime(),
                            "13"
                        )
                    }
                    1 -> {
                        viewModel.getData(
                            getCurrentDate(),
                            getCurrentTime(),
                            "14"
                        )
                    }
                    2 -> {
                        viewModel.getData(
                            getCurrentDate(),
                            getCurrentTime(),
                            "14"
                        )
                    }
                }
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.with_filter_toolbar, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.filter -> {
                showSnackbar("U go to filter SUCCESS")
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun getCurrentDate(): String {
        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.ROOT)
        return sdf.format(Date())
    }

    private fun getCurrentTime(): String {
        val stf = SimpleDateFormat("HH", Locale.ROOT)
        stf.timeZone = TimeZone.getTimeZone("Europe/Moscow")
        return stf.format(Date())
    }
}
