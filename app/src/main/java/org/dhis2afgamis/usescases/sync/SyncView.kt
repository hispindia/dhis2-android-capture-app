package org.dhis2afgamis.usescases.sync

import org.dhis2afgamis.usescases.general.AbstractActivityContracts

interface SyncView : AbstractActivityContracts.View {
    fun setServerTheme(themeId: Int)
    fun setFlag(flagName: String?)
    fun goToLogin()
    fun setMetadataSyncStarted()
    fun setMetadataSyncSucceed()
    fun showMetadataFailedMessage(message: String?)
    fun setDataSyncStarted()
    fun setDataSyncSucceed()
    fun goToMain()
}
