package org.medhis2yes.usescases.login.auth

import android.content.Context
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.InputStreamReader
import org.medhis2yes.R

class OpenIdProviders(private val context: Context) {

    fun loadOpenIdProvider(onSuccess: (AuthServiceModel) -> Unit) {
        context.resources.openRawResource(R.raw.openid_config).use { resource ->
            val reader = BufferedReader(InputStreamReader(resource))
            val result = Gson().fromJson(reader, AuthServiceModel::class.java)
            onSuccess(result)
        }
    }
}
