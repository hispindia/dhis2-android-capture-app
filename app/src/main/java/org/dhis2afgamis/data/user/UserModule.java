package org.dhis2afgamis.data.user;


import org.dhis2afgamis.data.dagger.PerUser;
import org.hisp.dhis.android.core.D2;

import dagger.Module;
import dagger.Provides;

@Module
@PerUser
public class UserModule {

    @Provides
    @PerUser
    UserRepository userRepository(D2 d2) {
        return new UserRepositoryImpl(d2);
    }

}
