package com.example.biblioteca

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.view.Window
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.github.barteksc.pdfviewer.PDFView
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.android.synthetic.main.activity_lectura.*

class Lectura : AppCompatActivity() {

    // Tag
    val tag = "Biblioteca"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Establecemos el layout main
        setContentView(R.layout.activity_lectura)

        // Setup
        val bundle = intent.extras
        val email = bundle?.getString("email")
        val provider = bundle?.getString("provider")
        val libro = bundle?.getString("referencia")


        var myWebView = findViewById<WebView>(R.id.vistaLectura)
        myWebView.getSettings().setJavaScriptEnabled(true)
        myWebView.loadUrl("http://drive.google.com/viewerng/viewer?embedded=true&url=" + libro)
        myWebView.setWebViewClient(WebViewClient())
        myWebView.setInitialScale(1)
        myWebView.getSettings().setBuiltInZoomControls(true)
        myWebView.getSettings().setUseWideViewPort(true)

        myWebView.setWebChromeClient(WebChromeClient());

        setup()

        // Guardado de datos
        val prefs: SharedPreferences.Editor =
            getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE).edit()
        prefs.putString("email", email)
        prefs.putString("provider", provider)
        prefs.apply()


    }

    private fun setup() {

        goBack.setOnClickListener() {
            // vuelta atras
            onBackPressed()
        }

    }

}