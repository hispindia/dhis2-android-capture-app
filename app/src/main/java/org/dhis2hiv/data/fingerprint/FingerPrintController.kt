package org.dhis2hiv.data.fingerprint

import co.infinum.goldfinger.Goldfinger
import io.reactivex.Observable

interface FingerPrintController {
    fun hasFingerPrint(): Boolean
    fun authenticate(promptParams: Goldfinger.PromptParams): Observable<FingerPrintResult>
    fun cancel()
}
