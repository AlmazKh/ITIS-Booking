package com.almaz.itis_booking.di.module

import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(
        private val context: Context
) {
    @Provides
    @Singleton
    fun provideContext(): Context = context
}
