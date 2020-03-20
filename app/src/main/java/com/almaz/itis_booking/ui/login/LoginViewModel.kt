package com.almaz.itis_booking.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
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
) : BaseViewModel() {

    val loginState = MutableLiveData<ScreenState<LoginState>>()

    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {
        loginState.value = ScreenState.Loading
        disposables.add(
            loginInteractor.loginWithGoogle(acct)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    loginState.value = ScreenState.Render(LoginState.SUCCESS_LOGIN)
                }, {
                    loginState.value = ScreenState.Render(LoginState.ERROR)
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

    companion object {
        internal const val RC_SIGN_IN = 228
    }
}
