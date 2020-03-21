package com.almaz.itis_booking.di.component

import androidx.appcompat.app.AppCompatActivity
import com.almaz.itis_booking.di.module.MapModule
import com.almaz.itis_booking.di.scope.ScreenScope
import com.almaz.itis_booking.ui.map.MapFragment
import dagger.BindsInstance
import dagger.Subcomponent

@Subcomponent(
        modules = [
            MapModule::class
        ]
)
@ScreenScope
interface MapComponent {
    @Subcomponent.Builder
    interface Builder {
        @BindsInstance
        fun withActivity(activity: AppCompatActivity): Builder
        fun build(): MapComponent
    }

    fun inject(mapFragment: MapFragment)
}