package com.example.biblioteca

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
 enum class ProviderType{
    BASIC
}
class MainPage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_page)
    }
}