package org.fpandhis2.utils

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import org.fpandhis2.R
import org.fpandhis2.databinding.ActivityWebviewBinding
import org.fpandhis2.usescases.general.ActivityGlobalAbstract

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
