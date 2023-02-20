package org.dhis2_haparent.form.model

import kotlinx.coroutines.CoroutineDispatcher

interface DispatcherProvider {
    fun io(): CoroutineDispatcher
    fun computation(): CoroutineDispatcher
    fun ui(): CoroutineDispatcher
}
