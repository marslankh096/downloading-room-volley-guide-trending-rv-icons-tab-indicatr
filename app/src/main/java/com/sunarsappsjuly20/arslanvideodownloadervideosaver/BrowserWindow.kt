package com.sunarsappsjuly20.arslanvideodownloadervideosaver

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.core.os.bundleOf

class BrowserWindow : Fragment() {

    private lateinit var webView: WebView

    companion object {
        private const val ARG_URL = "ARG_URL"

        fun newInstance(url: String): BrowserWindow {
            val args = bundleOf(ARG_URL to url)
            val fragment = BrowserWindow()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_browser_window, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        webView = view.findViewById(R.id.webView)
        webView.settings.javaScriptEnabled = true
        webView.webViewClient = WebViewClient()

        val url = arguments?.getString(ARG_URL)
        url?.let {
            webView.loadUrl(it)
        }
    }

    fun unhideWindow() {
        view?.visibility = View.VISIBLE
    }
}
