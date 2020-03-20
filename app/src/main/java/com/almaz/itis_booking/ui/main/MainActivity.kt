package com.almaz.itis_booking.ui.main

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.almaz.itis_booking.App
import com.almaz.itis_booking.R
import com.almaz.itis_booking.ui.base.BaseActivity
import com.almaz.itis_booking.ui.bookings.BookingsListFragment
import com.almaz.itis_booking.ui.login.LoginFragment
import com.almaz.itis_booking.ui.map.MapFragment
import com.almaz.itis_booking.ui.notification.NotificationsListFragment
import com.almaz.itis_booking.ui.profile.ProfileFragment
import com.almaz.itis_booking.ui.timetable.TimetableFragment
import com.almaz.itis_booking.utils.ViewModelFactory
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : BaseActivity() {

    @Inject
    lateinit var viewModeFactory: ViewModelFactory
    private lateinit var viewModel: MainViewModel

    override val layoutId: Int
        get() = R.layout.activity_main

    override fun inject() = App.appComponent
            .mainComponent()
            .withActivity(this)
            .build()
            .inject(this)

    override fun setupView() {
        setSupportActionBar(toolbar)
        navigation.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
        viewModel = ViewModelProvider(this, this.viewModeFactory)
                .get(MainViewModel::class.java)

        viewModel.checkAuthUser()
        observeIsLoginedLiveData()
    }

    private fun observeIsLoginedLiveData() =
        viewModel.isLoginedLiveData.observe(this, Observer { response ->
            if (response.data != null) {
                if (response.data) {
                    navigation.selectedItemId = R.id.navigation_timetable
                    navigateTo(TimetableFragment.toString(), null)
                } else {
                    navigateTo(LoginFragment.toString(), null)
                }
            }
        })

    private val onNavigationItemSelectedListener =
            BottomNavigationView.OnNavigationItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.navigation_map -> {
                        navigateTo(MapFragment.toString(), null)
                    }
                    R.id.navigation_bookings -> {
                        navigateTo(BookingsListFragment.toString(), null)
                    }
                    R.id.navigation_timetable -> {
                        navigateTo(TimetableFragment.toString(), null)
                    }
                    R.id.navigation_notification -> {
                        navigateTo(NotificationsListFragment.toString(), null)
                    }
                    R.id.navigation_profile -> {
                        navigateTo(ProfileFragment.toString(), null)
                    }
                    else -> {
                        return@OnNavigationItemSelectedListener false
                    }
                }
                return@OnNavigationItemSelectedListener true
            }

    fun navigateTo(fragment: String, arguments: Bundle?) {
        val transaction = supportFragmentManager.beginTransaction()
        when (fragment) {
            MapFragment.toString() -> {
                transaction.replace(
                        R.id.main_container,
                        MapFragment.newInstance()
                )
            }
            BookingsListFragment.toString() -> {
                transaction.replace(
                        R.id.main_container,
                        BookingsListFragment.newInstance()
                )
            }
            TimetableFragment.toString() -> {
                transaction.replace(
                        R.id.main_container,
                        TimetableFragment.newInstance()
                )
            }
            NotificationsListFragment.toString() -> {
                transaction.replace(
                        R.id.main_container,
                        NotificationsListFragment.newInstance()
                )
            }
            ProfileFragment.toString() -> {
                transaction.replace(
                        R.id.main_container,
                        ProfileFragment.newInstance()
                )
            }
            LoginFragment.toString() -> {
                transaction.replace(
                    R.id.main_container,
                    LoginFragment.newInstance()
                )
            }
        }
        transaction.addToBackStack(null)
        transaction.commit()
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStack()
            getTopFragment()?.let {
                supportFragmentManager.beginTransaction()
                        .remove(it)
                        .commitNow()
            }
            setBottomNavSelectedItem(getTopFragment())
        } else {
            super.onBackPressed()
        }
    }

    private fun getTopFragment(): Fragment? {
        val fragmentList = supportFragmentManager.fragments
        var top: Fragment? = null
        for (i in fragmentList.indices.reversed()) {
            top = fragmentList[i] as Fragment
            return top
        }
        return top
    }

    private fun setBottomNavSelectedItem(fragment: Fragment?) {
        when (fragment) {
            is MapFragment -> {
                navigation.selectedItemId = R.id.navigation_map
            }
            is BookingsListFragment -> {
                navigation.selectedItemId = R.id.navigation_bookings
            }
            is TimetableFragment -> {
                navigation.selectedItemId = R.id.navigation_timetable
            }
            is NotificationsListFragment -> {
                navigation.selectedItemId = R.id.navigation_notification
            }
            is ProfileFragment -> {
                navigation.selectedItemId = R.id.navigation_profile
            }
        }
    }

    fun showLoading(show: Boolean) {
        if (show) {
            pb_main.visibility = View.VISIBLE
            window.setFlags(
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
            )
        } else {
            pb_main.visibility = View.GONE
            window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        }
    }
}
