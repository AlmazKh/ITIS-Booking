package com.almaz.itis_booking.di.component

import androidx.appcompat.app.AppCompatActivity
import com.almaz.itis_booking.di.module.AppModule
import com.almaz.itis_booking.di.module.ViewModelFactoryModule
import com.almaz.itis_booking.di.module.ViewModelModule
import com.almaz.itis_booking.ui.main.MainActivity
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        ViewModelModule::class,
        ViewModelFactoryModule::class
    ]
)
interface AppComponent {
    fun mainComponent(): MainComponent.Builder
}
