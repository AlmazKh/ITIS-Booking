package com.almaz.itis_booking.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.almaz.itis_booking.App
import com.almaz.itis_booking.R
import com.almaz.itis_booking.ui.base.BaseFragment
import com.almaz.itis_booking.ui.login.LoginViewModel.Companion.RC_SIGN_IN
import com.almaz.itis_booking.ui.profile.ProfileFragment
import com.almaz.itis_booking.utils.LoginState
import com.almaz.itis_booking.utils.ScreenState
import com.almaz.itis_booking.utils.ViewModelFactory
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import kotlinx.android.synthetic.main.fragment_login.view.*
import javax.inject.Inject

class LoginFragment : BaseFragment() {
    @Inject
    lateinit var viewModeFactory: ViewModelFactory
    private lateinit var viewModel: LoginViewModel
    @Inject
    lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.appComponent
            .authComponent()
            .withActivity(activity as AppCompatActivity)
            .build()
            .inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_login, container, false)
        init(rootView)
        return rootView
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        viewModel.onGoogleIntentResult(requestCode, data)
    }

    private fun init(view: View) {
        setToolbarAndBottomNavVisibility(
                toolbarVisibility = View.GONE,
                bottomNavVisibility = View.GONE
        )

        viewModel = ViewModelProvider(this, this.viewModeFactory)
            .get(LoginViewModel::class.java)

        view.btn_login_by_google.setOnClickListener {
            onGoogleSignInClick()
        }

        observeLoginState()
    }

    private fun onGoogleSignInClick() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    private fun observeLoginState() {
        viewModel.loginState.observe(viewLifecycleOwner, Observer {
            it?.let { screenState -> updateUI(screenState) }
        })
    }

    private fun updateUI(screenState: ScreenState<LoginState>?) {
        when (screenState) {
            ScreenState.Loading -> rootActivity.showLoading(true)
            is ScreenState.Render -> processLoginState(screenState.renderState)
        }
    }

    private fun processLoginState(renderState: LoginState) {
        rootActivity.showLoading(false)
        when (renderState) {
            LoginState.SUCCESS_LOGIN -> rootActivity.navigateTo(
                ProfileFragment.toString(),
                null
            )
            LoginState.SUCCESS_REGISTER -> rootActivity.navigateTo(
                ProfileFragment.toString(),
                null
            )
            LoginState.ERROR -> view?.let {
                showSnackbar(getString(R.string.snackbar_error_message))
            }
        }
    }

    companion object {
        fun newInstance() = LoginFragment()
    }
}