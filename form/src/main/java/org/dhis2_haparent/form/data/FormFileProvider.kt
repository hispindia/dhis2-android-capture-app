package org.dhis2_haparent.form.data

import android.content.Context

object FormFileProvider {

    lateinit var fileProviderAuthority: String

    fun init(context: Context) {
        fileProviderAuthority = "${context.packageName}.form.data.FormFileProvider"
    }
}
