package org.fpandhis2.data.user;


import org.fpandhis2.data.dagger.PerUser;
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
