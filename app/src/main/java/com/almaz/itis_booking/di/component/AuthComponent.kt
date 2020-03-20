package com.almaz.itis_booking.di.component

import androidx.appcompat.app.AppCompatActivity
import com.almaz.itis_booking.di.module.AuthModule
import com.almaz.itis_booking.di.scope.ScreenScope
import com.almaz.itis_booking.ui.login.LoginFragment
import dagger.BindsInstance
import dagger.Subcomponent

@Subcomponent(
    modules = [
        AuthModule::class
    ]
)
@ScreenScope
interface AuthComponent {

    @Subcomponent.Builder
    interface Builder {
        @BindsInstance
        fun withActivity(activity: AppCompatActivity): Builder

        fun build(): AuthComponent
    }

    fun inject(loginFragment: LoginFragment)
}