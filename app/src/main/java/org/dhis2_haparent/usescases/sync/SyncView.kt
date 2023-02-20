package org.dhis2_haparent.usescases.sync

import org.dhis2_haparent.usescases.general.AbstractActivityContracts

interface SyncView : AbstractActivityContracts.View {
    fun setServerTheme(themeId: Int)
    fun setFlag(flagName: String?)
    fun goToLogin()
    fun setMetadataSyncStarted()
    fun setMetadataSyncSucceed()
    fun showMetadataFailedMessage(message: String?)
    fun goToMain()
}
