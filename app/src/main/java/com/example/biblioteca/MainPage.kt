package com.example.biblioteca

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.OnBackPressedDispatcher
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main_page.*

enum class ProviderType{
    BASIC,GOOGLE,FACEBOOK,TWITTER
}
class MainPage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_page)

        // Setup
        val bundle= intent.extras
        val conf = bundle?.getString("Config")
        setup()
    }
    private fun setup(){
        title = "Biblioteca"
        configButton.setOnClickListener(){}
        logOutButton.setOnClickListener(){
            FirebaseAuth.getInstance().signOut()
            OnBackPressedDispatcher()
            backToLogin()
        }
    }

    // volver al login
    private fun backToLogin() {
        val authPageIntent = Intent(this, AuthActivity::class.java).apply {}
        startActivity(authPageIntent)
    }
}