package com.almaz.itis_booking.di.component

import com.almaz.itis_booking.di.module.*
import com.almaz.itis_booking.ui.main.MainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        ViewModelModule::class,
        ViewModelFactoryModule::class,
        RepoModule::class,
        NetModule::class
    ]
)
interface AppComponent {
    fun inject(mainActivity: MainActivity)
    fun authComponent(): AuthComponent.Builder
    fun mapComponent(): MapComponent.Builder
    fun timetableComponent(): TimetableComponent.Builder
    fun profileComponent(): ProfileComponent.Builder
}
