package com.example.biblioteca

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.facebook.login.LoginManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_auth.*
import kotlinx.android.synthetic.main.activity_main_page.*
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

        val genera_Id: Int = 1110
        val genera = Button(this)
        genera.setText("En español")
        genera.setLayoutParams(ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT))
        genera.setId(genera_Id)
        genera.x = 0f
        genera.y = 500f
        genera.setOnClickListener(){
            // Creamos la lista de libros
            db.collection("Libros").get().addOnSuccessListener { coleccion ->
                val iterador = coleccion.iterator()
                iterador.forEach{
                    libros.add(it.toObject(Libro::class.java))
                    if (it.id != null){
                        Log.d(tag,"Está entrando en el bucle")
                    }
                    Log.d(tag,"Datos documento: ID-${it.id}, DATA-${it.data}, " +
                            "REFERENCE-${it.reference}, METADATA-${it.metadata}")
                }
                Log.d(tag,"Exito al escuchar libros: "+libros.toString()+" Colección: "+coleccion.toString())
            }.addOnFailureListener {
                Log.w(tag,"Fallo al conectar", it)
            }
            Log.d(tag,"Exito metiendo los libros"+libros.toString())
        }
        constraintLayout.addView(genera)

        // Creamos los botones en bucle
        for (l in libros){
            val libro_Id: Int = 1111 + l.id
            val libro = Button(this)
            libro.setText(l.titulo)
            //libro.setText("Libro generado")
            libro.setLayoutParams(ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT))
            libro.setId(libro_Id)
            libro.x = 0f
            libro.y = 500f
            libro.setOnClickListener(){
                    // Abre el libro
            }
            llBotones.addView(libro)
        }

        // Guardado de datos
        val prefs: SharedPreferences.Editor = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE).edit()
        prefs.putString("email", email)
        prefs.putString("provider", provider)
        prefs.apply()
    }

    private fun setup() {
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