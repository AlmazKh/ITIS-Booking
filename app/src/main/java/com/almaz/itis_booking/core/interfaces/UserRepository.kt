package com.almaz.itis_booking.core.interfaces

import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import io.reactivex.Completable
import io.reactivex.Single

interface UserRepository {
    fun checkAuthUser(): Single<Boolean>
    fun loginWithGoogle(acct: GoogleSignInAccount): Completable
}