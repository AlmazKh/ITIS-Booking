package com.almaz.itis_booking.ui.main

import android.view.View
import android.view.WindowManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.almaz.itis_booking.App
import com.almaz.itis_booking.R
import com.almaz.itis_booking.ui.base.BaseActivity
import com.almaz.itis_booking.utils.AuthenticationState
import com.almaz.itis_booking.utils.ViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : BaseActivity() {

    @Inject
    lateinit var viewModeFactory: ViewModelFactory
    private lateinit var viewModel: MainViewModel

    val navController by lazy { findNavController(R.id.nav_host_fragment) }

    override val layoutId: Int
        get() = R.layout.activity_main

    override fun inject() = App.appComponent.inject(this)

    override fun setupView() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false);
        bottom_nav?.setupWithNavController(navController)

        viewModel = ViewModelProvider(this, this.viewModeFactory)
                .get(MainViewModel::class.java)

        observeAuthenticationState()
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp()
    }

    private fun observeAuthenticationState() =
        viewModel.authenticationState.observe(this, Observer { authState ->
            if (authState != null) {
                when (authState) {
                    AuthenticationState.UNAUTHENTICATED -> {
                        navController.navigate(R.id.loginFragment)
                    }
                    AuthenticationState.AUTHENTICATED -> {
                        navController.navigate(R.id.action_loginFragment_to_timetableFragment)
                    }
                    AuthenticationState.INVALID_AUTHENTICATION -> {
                        navController.navigate(R.id.loginFragment)
                    }
                }
            }
        })

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
