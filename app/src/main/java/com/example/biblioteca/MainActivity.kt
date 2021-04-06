package com.example.biblioteca

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.facebook.login.LoginManager
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_auth.*
import kotlinx.android.synthetic.main.activity_main_page.*

enum class ProviderType{
    BASIC,GOOGLE,FACEBOOK,TWITTER
}
class MainPage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_page)

        // Setup
        val bundle = intent.extras
        val email = bundle?.getString("email")
        val provider = bundle?.getString("provider")

        setup()

        // guardado de datos
        val prefs: SharedPreferences.Editor = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE).edit()
        prefs.putString("email", email)
        prefs.putString("provider", provider)
        prefs.apply()
    }

    private fun setup() {
        title = "Biblioteca"
        configButton.setOnClickListener() {
            val prefs: SharedPreferences = getSharedPreferences(
                    getString(R.string.prefs_file),
                    Context.MODE_PRIVATE
            )
            val email = prefs.getString("email", null)
            val provider = prefs.getString("provider", null)
            if (email != null && provider != null) {
                showConfig(email, ProviderType.valueOf(provider))
            }

        }
        logOutButton.setOnClickListener() {
            val bundle = intent.extras
            val provider = bundle?.getString("provider")

            // Borrado de datos
            val prefs: SharedPreferences.Editor = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE).edit()
            prefs.clear()
            prefs.apply()
            if (provider == ProviderType.FACEBOOK.name) {
                LoginManager.getInstance().logOut()
            }

            // desconexion base de datos
            FirebaseAuth.getInstance().signOut()

            // vuelta atras
            onBackPressed()

        }
    }

    // cambiar a configuracion
    private fun showConfig(email: String, provider: ProviderType) {
        val configPageIntent = Intent(this, ConfigActivity::class.java).apply {
            putExtra("email", email)
            putExtra("provider", provider.name)
        }
        startActivity(configPageIntent)
    }
}