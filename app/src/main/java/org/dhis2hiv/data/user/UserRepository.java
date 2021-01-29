package org.dhis2hiv.data.user;

import androidx.annotation.NonNull;

import org.hisp.dhis.android.core.user.UserCredentials;

import io.reactivex.Flowable;

public interface UserRepository {
    @NonNull
    Flowable<UserCredentials> credentials();
}
