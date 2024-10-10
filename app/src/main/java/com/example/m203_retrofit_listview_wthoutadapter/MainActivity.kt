package com.example.m203_retrofit_listview_wthoutadapter

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET


// Modèle de données
data class Smartphone(
    val id: Int,
    val nom: String,
    val prix: Double,
    val image: String
)




class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val addButt = findViewById<Button>(R.id.add)

        addButt.setOnClickListener {

            val intent = Intent(this, Add::class.java)
            startActivity(intent)

        }

        val listView = findViewById<ListView>(R.id.lv)

        // Configurer Retrofit
        val retrofit = Retrofit.Builder()
            .baseUrl("https://apiyes.net/")  // Remplacez par l'URL de votre API
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        // Créer une instance de l'interface ApiService
        val apiService = retrofit.create(ApiService::class.java)

        // Faire un appel réseau pour récupérer les données
        val call = apiService.getSmartphones()
        call.enqueue(object : Callback<List<Smartphone>> {
            override fun onResponse(call: Call<List<Smartphone>>, response: Response<List<Smartphone>>) {
                if (response.isSuccessful) {
                    val smartphones = response.body() ?: emptyList()

                    // Extraire les noms des smartphones en utilisant une boucle for
                    val smartphoneNames = mutableListOf<String>()
                    for (s in smartphones) {
                        smartphoneNames.add(s.nom+" - "+s.prix+ " MAD")
                    }

                    // Utiliser un ArrayAdapter avec simple_list_item_1
                    val adapter = ArrayAdapter(applicationContext, android.R.layout.simple_list_item_1, smartphoneNames)
                    listView.adapter = adapter
                } else {
                    Toast.makeText(this@MainActivity, "Erreur lors de la récupération des données", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<Smartphone>>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Échec de la connexion à l'API", Toast.LENGTH_SHORT).show()
            }
        })


    }


}
