package com.example.biblioteca

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
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
        val email = bundle?.getString("email")
        setup(email ?: "")
    }
    private fun setup(email:String){
        title = "Biblioteca"
        emailTextView.text = email
        logOutbutton.setOnClickListener(){
            FirebaseAuth.getInstance().signOut()
            OnBackPressedDispatcher()
        }
    }
}