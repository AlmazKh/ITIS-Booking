package com.almaz.itis_booking.di.module

import com.almaz.itis_booking.core.interfaces.TimetableRepository
import com.almaz.itis_booking.core.interfaces.UserRepository
import com.almaz.itis_booking.data.TimetableRepositoryImpl
import com.almaz.itis_booking.data.UserRepositoryImpl
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
interface RepoModule {
    @Binds
    @Singleton
    fun bindUserRepository(userRepositoryImpl: UserRepositoryImpl): UserRepository

    @Binds
    @Singleton
    fun bindTimetableRepository(timetableRepositoryImpl: TimetableRepositoryImpl): TimetableRepository
}

