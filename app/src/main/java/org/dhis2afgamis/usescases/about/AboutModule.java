package org.dhis2afgamis.usescases.about;

import androidx.annotation.NonNull;

import org.dhis2afgamis.data.dagger.PerFragment;
import org.dhis2afgamis.data.schedulers.SchedulerProvider;
import org.dhis2afgamis.data.user.UserRepository;
import org.hisp.dhis.android.core.D2;

import dagger.Module;
import dagger.Provides;

/**
 * QUADRAM. Created by ppajuelo on 05/07/2018.
 */
@Module
public class AboutModule {

    @Provides
    @PerFragment
    AboutContracts.AboutPresenter providesPresenter(@NonNull D2 d2, SchedulerProvider provider, @NonNull UserRepository userRepository) {
        return new AboutPresenterImpl(d2, provider, userRepository);
    }
}
