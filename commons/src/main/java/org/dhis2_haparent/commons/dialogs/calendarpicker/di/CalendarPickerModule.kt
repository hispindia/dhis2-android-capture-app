package org.dhis2_haparent.commons.dialogs.calendarpicker.di

import dagger.Module
import dagger.Provides
import org.dhis2_haparent.commons.dialogs.calendarpicker.CalendarPickerRepository
import org.dhis2_haparent.commons.dialogs.calendarpicker.CalendarPickerRepositoryImpl
import org.dhis2_haparent.commons.prefs.PreferenceProvider

@Module
class CalendarPickerModule {

    @Provides
    fun providesCalendarPickerPresenter(
        preferences: PreferenceProvider
    ): CalendarPickerRepository {
        return CalendarPickerRepositoryImpl(preferences)
    }
}
