package com.example.biblioteca


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import kotlinx.android.synthetic.main.activity_config.*


class ConfigActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_config)


        // setup
        val bundle = intent.extras
        val email = bundle?.getString("email")
        val provider = bundle?.getString("provider")
        setup(email ?:"",provider ?:"")

    }
    private fun setup(email:String, provider:String ){
        title = "Configuración"
        emailTextView.text = email
        providerTextView.text = provider


        backButton.setOnClickListener(){
            onBackPressed()
        }
    }
}