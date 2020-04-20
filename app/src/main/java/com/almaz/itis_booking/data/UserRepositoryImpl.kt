package com.almaz.itis_booking.data

import com.almaz.itis_booking.core.interfaces.UserRepository
import com.almaz.itis_booking.core.model.User
import com.almaz.itis_booking.core.model.remote.UserRemote
import com.almaz.itis_booking.data.api.BookingApi
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class UserRepositoryImpl
@Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val bookingApi: BookingApi,
    private val priorityHolder: PriorityHolder
) : UserRepository {

    override fun checkAuthUser(): Single<Boolean> = Single.just(firebaseAuth.currentUser != null)

    override fun loginWithGoogle(acct: GoogleSignInAccount): Completable {
        return Completable.create { emitter ->
            firebaseAuth.signInWithCredential(
                GoogleAuthProvider.getCredential(
                    acct.idToken,
                    null
                )
            ).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    emitter.onComplete()
                } else {
                    emitter.onError(task.exception ?: Exception(""))
                }
            }
        }
        /*.doOnComplete {
            TODO adding user into DB
        }*/
    }

    /*override fun getCurrentUser(): Single<User> {
        return Single.just(
            User(
                1,
                "Алмаз Хамеджанов",
                "Высшая школа ИТИС",
                "11-702",
                "Студент",
                "hamedzhanovalmaz@gmail.com",
                "https://lh3.googleusercontent.com/-Y4boBb2V0cw/AAAAAAAAAAI/AAAAAAAAAE0/Wuyi-gq4hhQ/s96-c/photo.jpg"
            )
        )
    }*/

    override fun getCurrentUser(): Single<User> =
        Single.fromObservable(firebaseAuth.currentUser?.email?.let { it ->
            bookingApi.getUserByEmail(
                it
            ).map {
                mapRemoteUserToLocal(it)
            }
        })

    override fun getCurrentUserPriorityValue(): Single<Int> =
        Single.fromObservable(firebaseAuth.currentUser?.email?.let { it ->
            bookingApi.getUserByEmail(
                it
            ).map {
                it.priority
            }
        })

    private fun mapRemoteUserToLocal(remote: UserRemote): User {
        return User(
            remote.id,
            remote.name,
            remote.institute,
            remote.groupNumber,
            priorityHolder.getPriorityNameByValue(remote.priority),
            remote.email,
            remote.photo
        )
    }
}
