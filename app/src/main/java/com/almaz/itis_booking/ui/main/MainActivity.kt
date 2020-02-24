package com.almaz.itis_booking.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.almaz.itis_booking.App
import com.almaz.itis_booking.R
import com.almaz.itis_booking.ui.base.BaseActivity
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
    }

    private val onNavigationItemSelectedListener =
            BottomNavigationView.OnNavigationItemSelectedListener { item ->
                when (item.itemId) {
                    /*R.id.nav_profile -> {
                        navigateTo(MyProfileFragment.toString(), null)
                    }
                    ...*/
                    else -> {
                        return@OnNavigationItemSelectedListener false
                    }
                }
                return@OnNavigationItemSelectedListener true
            }

    fun navigateTo(fragment: String, arguments: Bundle?) {
        val transaction =
                supportFragmentManager.beginTransaction()
        when (fragment) {
            /*LoginFragment.toString() -> {
                transaction.replace(
                    R.id.main_container,
                    LoginFragment.newInstance()
                )
            }*/
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
            /*is MyProfileFragment -> {
                bottom_navigation.selectedItemId = R.id.nav_profile
            }
            is PeopleFragment -> {
                bottom_navigation.selectedItemId = R.id.nav_people
            }*/
        }
    }
}
