package com.example.biblioteca

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
        val provider = bundle?.getString("provider")
        val conf = bundle?.getString("Config")
        setup()

        // guardado de datos
        val prefs: SharedPreferences.Editor = getSharedPreferences(getString(R.string.prefs_file),Context.MODE_PRIVATE).edit()
        prefs.putString("email",email)
        prefs.putString("provider",provider)
        prefs.apply()
    }
    private fun setup(){
        title = "Biblioteca"
        configButton.setOnClickListener(){
            backToLogin()
        }
        logOutButton.setOnClickListener(){

            // Borrado de datos
            val prefs: SharedPreferences.Editor = getSharedPreferences(getString(R.string.prefs_file),Context.MODE_PRIVATE).edit()
            prefs.clear()
            prefs.apply()

            // desconexion base de datos
            FirebaseAuth.getInstance().signOut()

            // vuelta atras
            onBackPressed()

        }
    }

    // cambiar a configuracion
     private fun backToLogin() {
        val authPageIntent = Intent(this, AuthActivity::class.java).apply {}
        startActivity(authPageIntent)
    }
}