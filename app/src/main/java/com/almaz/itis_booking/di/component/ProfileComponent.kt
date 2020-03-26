package com.almaz.itis_booking.di.component

import androidx.appcompat.app.AppCompatActivity
import com.almaz.itis_booking.di.module.ProfileModule
import com.almaz.itis_booking.di.scope.ScreenScope
import com.almaz.itis_booking.ui.profile.ProfileFragment
import dagger.BindsInstance
import dagger.Subcomponent

@Subcomponent(
    modules = [
        ProfileModule::class
    ]
)
@ScreenScope
interface ProfileComponent {
    @Subcomponent.Builder
    interface Builder {
        @BindsInstance
        fun withActivity(activity: AppCompatActivity): Builder
        fun build(): ProfileComponent
    }

    fun inject(profileFragment: ProfileFragment)
}
