package com.almaz.itis_booking.di.component

import androidx.appcompat.app.AppCompatActivity
import com.almaz.itis_booking.di.module.MainModule
import com.almaz.itis_booking.di.scope.ScreenScope
import com.almaz.itis_booking.ui.main.MainActivity
import dagger.BindsInstance
import dagger.Subcomponent

@Subcomponent(
        modules = [
            MainModule::class
        ]
)
@ScreenScope
interface MainComponent {
    @Subcomponent.Builder
    interface Builder {
        @BindsInstance
        fun withActivity(activity: AppCompatActivity): Builder
        fun build(): MainComponent
    }

    fun inject(mainActivity: MainActivity)
}