package com.almaz.itis_booking

import android.app.Application
import com.almaz.itis_booking.di.component.AppComponent
import com.almaz.itis_booking.di.component.DaggerAppComponent
import com.almaz.itis_booking.di.module.AppModule

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .build()
    }


    companion object {
        lateinit var appComponent: AppComponent
    }
}
