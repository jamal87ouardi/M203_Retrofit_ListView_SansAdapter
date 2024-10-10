package com.example.m203_retrofit_listview_wthoutadapter

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
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

data class AddResponse(
    val status: Int,
    val status_message: String
)

class Add : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val editTextName = findViewById<EditText>(R.id.editTextName)
        val editTextPrice = findViewById<EditText>(R.id.editTextPrice)
        val editTextImageUrl = findViewById<EditText>(R.id.editTextImageUrl)
        val buttonAddSmartphone = findViewById<Button>(R.id.buttonAddSmartphone)

        // Initialisation de Retrofit
        val retrofit = Retrofit.Builder()
            .baseUrl("https://apiyes.net/") // Remplacez par votre URL API
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(ApiService::class.java)

        buttonAddSmartphone.setOnClickListener {
            val name = editTextName.text.toString().trim()
            val priceStr = editTextPrice.text.toString().trim()
            val imageUrl = editTextImageUrl.text.toString().trim()

            if (name.isEmpty() || priceStr.isEmpty() || imageUrl.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()

            } else {

                val price = priceStr.toDouble()




                // Appel à l'API pour ajouter le smartphone
                apiService.addSmartphone(name,price,imageUrl).enqueue(object : Callback<AddResponse> {
                    override fun onResponse(
                        call: Call<AddResponse>,
                        response: Response<AddResponse>
                    ) {
                        if (response.isSuccessful) {
                            val addResponse = response.body()
                            if (addResponse != null) {

                                Toast.makeText(
                                    applicationContext,
                                    addResponse.status_message,
                                    Toast.LENGTH_LONG
                                ).show()
                                if (addResponse.status == 1) {
                                    finish() // Fermer l'activité ou réinitialiser les champs
                                }
                            }
                        } else {
                            Toast.makeText(
                                applicationContext,
                                "Failed to add smartphone",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }

                    override fun onFailure(call: Call<AddResponse>, t: Throwable) {
                        Toast.makeText(
                            applicationContext,
                            "Error: ${t.message}",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                })
            }
        }

    }


}