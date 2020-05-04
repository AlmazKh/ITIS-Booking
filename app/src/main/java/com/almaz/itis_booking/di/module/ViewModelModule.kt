package com.almaz.itis_booking.di.module

import androidx.lifecycle.ViewModel
import com.almaz.itis_booking.ui.bookings.BookingsViewModel
import com.almaz.itis_booking.ui.login.LoginViewModel
import com.almaz.itis_booking.ui.main.MainViewModel
import com.almaz.itis_booking.ui.profile.ProfileViewModel
import com.almaz.itis_booking.ui.timetable.CabinetViewModel
import com.almaz.itis_booking.ui.timetable.FilterViewModel
import com.almaz.itis_booking.ui.timetable.TimetableViewModel
import dagger.Binds
import dagger.MapKey
import dagger.Module
import dagger.multibindings.IntoMap
import kotlin.reflect.KClass

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel::class)
    abstract fun bindLoginViewModel(loginViewModel: LoginViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindMainViewModel(mainViewModel: MainViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(TimetableViewModel::class)
    abstract fun bindTimetableViewModel(timetableViewModel: TimetableViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ProfileViewModel::class)
    abstract fun bindProfileViewModel(profileViewModel: ProfileViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CabinetViewModel::class)
    abstract fun bindCabinetViewModel(cabinetViewModel: CabinetViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(FilterViewModel::class)
    abstract fun bindFilterViewModel(filterViewModel: FilterViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(BookingsViewModel::class)
    abstract fun bindBookingsViewModel(bookingsViewModel: BookingsViewModel): ViewModel
}

@Target(
    AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY_GETTER,
    AnnotationTarget.PROPERTY_SETTER
)
@MapKey
annotation class ViewModelKey(val value: KClass<out ViewModel>)