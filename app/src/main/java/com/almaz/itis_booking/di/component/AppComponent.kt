package com.almaz.itis_booking.di.component

import com.almaz.itis_booking.di.module.AppModule
import com.almaz.itis_booking.di.module.RepoModule
import com.almaz.itis_booking.di.module.ViewModelFactoryModule
import com.almaz.itis_booking.di.module.ViewModelModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        ViewModelModule::class,
        ViewModelFactoryModule::class,
        RepoModule::class
    ]
)
interface AppComponent {
    fun mainComponent(): MainComponent.Builder
    fun authComponent(): AuthComponent.Builder
    fun mapComponent(): MapComponent.Builder
    fun timetableComponent(): TimetableComponent.Builder
}
