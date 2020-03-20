package com.almaz.itis_booking.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.almaz.itis_booking.core.interactors.LoginInteractor
import com.almaz.itis_booking.ui.base.BaseViewModel
import com.almaz.itis_booking.utils.LoginState
import com.almaz.itis_booking.utils.ScreenState
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.GoogleApiClient
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject

class LoginViewModel
@Inject constructor(
    private val loginInteractor: LoginInteractor
) : BaseViewModel(), GoogleApiClient.OnConnectionFailedListener {

    val loginState: LiveData<ScreenState<LoginState>>
        get() = mLoginState

    private val mLoginState: MutableLiveData<ScreenState<LoginState>> = MutableLiveData()

    fun onGoogleSignInClick(googleSignInClient: GoogleSignInClient, activity: AppCompatActivity) {
        val signInIntent = googleSignInClient.signInIntent
        activity.startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {
        mLoginState.value = ScreenState.Loading
        disposables.add(
            loginInteractor.loginWithGoogle(acct)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    mLoginState.value = ScreenState.Render(LoginState.SUCCESS_LOGIN)
                }, {
                    mLoginState.value = ScreenState.Render(LoginState.ERROR)
                    it.printStackTrace()
                })
        )
    }

    fun onGoogleIntentResult(requestCode: Int, data: Intent?) {
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                account?.let { firebaseAuthWithGoogle(it) }
            } catch (e: ApiException) {
                // login_btn_background Sign In failed, update UI appropriately
                // ...
            }
        }
    }

    override fun onConnectionFailed(result: ConnectionResult) {
        mLoginState.value = ScreenState.Render(LoginState.ERROR)
    }

    companion object {
        internal const val RC_SIGN_IN = 228
    }
}
