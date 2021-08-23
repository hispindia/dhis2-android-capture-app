package org.medhis2yes.usescases.about

import org.hisp.dhis.android.core.user.UserCredentials

interface AboutView {
    fun renderUserCredentials(userCredentialsModel: UserCredentials?)
    fun renderServerUrl(serverUrl: String?)
    fun navigateToPrivacyPolicy()
}
