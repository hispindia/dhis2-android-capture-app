package org.dhis2_haparent.form.model.coroutine

import kotlinx.coroutines.Dispatchers
import org.dhis2_haparent.form.model.DispatcherProvider

class FormDispatcher : DispatcherProvider {
    override fun io() = Dispatchers.IO

    override fun computation() = Dispatchers.Default

    override fun ui() = Dispatchers.Main
}
