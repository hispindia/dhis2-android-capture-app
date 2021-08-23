package org.medhis2yes.usescases.sync

import org.medhis2yes.usescases.general.AbstractActivityContracts

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
