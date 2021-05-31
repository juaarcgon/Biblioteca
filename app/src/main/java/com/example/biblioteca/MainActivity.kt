package com.example.biblioteca

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.DocumentsContract
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.widget.*
import com.facebook.login.LoginManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_auth.*
import kotlinx.android.synthetic.main.activity_lectura.*
import kotlinx.android.synthetic.main.activity_main_page.*
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream
import java.net.HttpURLConnection
import java.net.URL
import kotlin.math.log
import kotlin.collections.Map as Map

enum class ProviderType{
    BASIC,GOOGLE,FACEBOOK,TWITTER
}
class MainPage : AppCompatActivity() {

    // Acceso a la base de datos
    private val db = FirebaseFirestore.getInstance()
    // Tag
    val tag = "Biblioteca"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Establecemos el layout main
        setContentView(R.layout.activity_main_page)

        // Setup
        val bundle = intent.extras
        val email = bundle?.getString("email")
        val provider = bundle?.getString("provider")

        setup()

        var libros = mutableListOf<Libro>()

        // Creamos la lista de libros
        db.collection("Libros").get().addOnSuccessListener { coleccion ->
            for (doc in coleccion) {
                libros.add(doc.toObject(Libro::class.java));
            }
            // Creamos los botones en bucle
            for (l in libros){

                val libro_Id: Int = 1110 + l.ID
                val libro = Button(this)
                libro.setText(l.Titulo)
                libro.setLayoutParams(ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT))
                libro.setId(libro_Id)
                libro.x = 0f
                libro.y = 500f
                libro.setOnClickListener(){
                    // Abre el libro
                    val lecturaI = Intent(this, Lectura::class.java).apply {
                        putExtra("email", email)
                        putExtra("provider", provider)
                        putExtra("referencia", l.Referencia)
                    }
                    startActivity(lecturaI)
                }
                    llBotones.addView(libro)
            }
        }.addOnFailureListener {
            Log.w(tag,"Fallo al conectar", it)
        }

        // Guardado de datos
        val prefs: SharedPreferences.Editor = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE).edit()
        prefs.putString("email", email)
        prefs.putString("provider", provider)
        prefs.apply()
    }

    private fun setup() {

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


}