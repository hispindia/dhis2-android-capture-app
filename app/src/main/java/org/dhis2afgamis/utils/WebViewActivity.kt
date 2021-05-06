package org.dhis2afgamis.utils

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import org.dhis2afgamis.R
import org.dhis2afgamis.databinding.ActivityWebviewBinding
import org.dhis2afgamis.usescases.general.ActivityGlobalAbstract

class WebViewActivity : ActivityGlobalAbstract() {

    companion object {
        const val WEB_VIEW_URL = "url"
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityWebviewBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_webview)

        val url = intent?.extras?.getString(WEB_VIEW_URL)

        url?.let {
            binding.webView.settings.javaScriptEnabled = true
            binding.webView.loadUrl(it)
        }
    }

    fun backToLogin(view: View) {
        finish()
    }

    override fun onBackPressed() {
        finish()
    }
}
