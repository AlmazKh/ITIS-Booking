package com.almaz.itis_booking.di.component

import androidx.appcompat.app.AppCompatActivity
import com.almaz.itis_booking.di.module.TimetableModule
import com.almaz.itis_booking.ui.timetable.CabinetFragment
import com.almaz.itis_booking.ui.timetable.TimetableFragment
import dagger.BindsInstance
import dagger.Subcomponent

@Subcomponent(
    modules = [
        TimetableModule::class
    ]
)
interface TimetableComponent {
    @Subcomponent.Builder
    interface Builder {
        @BindsInstance
        fun withActivity(activity: AppCompatActivity): Builder
        fun build(): TimetableComponent
    }

    fun inject(timetableFragment: TimetableFragment)
    fun inject(cabinetFragment: CabinetFragment)
}
