package org.dhis2_haparent.usescases.general

import android.content.Context
import org.dhis2_haparent.commons.resources.LocaleSelector
import org.dhis2_haparent.data.server.ServerComponent

fun ActivityGlobalAbstract.wrappedContextForLanguage(
    serverComponent: ServerComponent?,
    newBaseContext: Context
): Context {
    return if (serverComponent?.getD2()?.userModule()?.blockingIsLogged() == true) {
        LocaleSelector(newBaseContext, serverComponent.getD2()).updateUiLanguage()
    } else {
        newBaseContext
    }
}
