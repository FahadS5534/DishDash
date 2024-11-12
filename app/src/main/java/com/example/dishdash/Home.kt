package com.example.dishdash

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Home :AppCompatActivity() {
    lateinit var recylerView: RecyclerView
    lateinit var recyclerview2: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        recylerView = findViewById(R.id.recyclerView)
        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        recylerView.layoutManager = linearLayoutManager
        recyclerview2 = findViewById(R.id.NewRecipe)
        recyclerview2.layoutManager = linearLayoutManager
        fetchRandomRecipes()
//        val editText: EditText = findViewById(R.id.search)
//        editText.setOnClickListener {
//            val intent: Intent = Intent(this@Home, SearchActivity::class.java)
//            startActivity(intent)
//        }
    }

    private fun fetchRandomRecipes() {
        val api_key = "ff046b9c790046358cde6252d98f668a"
        val call = RetrofitClient.instance.getRandomRecipe(api_key, 10)
        call.enqueue(object : Callback<RandomRecipeResponse> {
            override fun onResponse(
                call: Call<RandomRecipeResponse>,
                response: Response<RandomRecipeResponse>
            ) {
                if (response.isSuccessful) {
                    val recipes = response.body()?.recipes ?: emptyList()
                    val adapter = RandomRecipeAdapter(this@Home, recipes)
                    recylerView.adapter = adapter
                    recyclerview2.adapter = adapter
                } else {
                    Log.e("MainActivity", "Error fetching random recipes: ${response.errorBody()}")
                    println("Error: ${response.errorBody()?.string() ?: "Unknown error"}")
                }
            }

            override fun onFailure(call: Call<RandomRecipeResponse>, t: Throwable) {
                Log.e("MainActivity", "Error fetching random recipes: ${t.message}", t)
            }

        })
    }

    private fun showToast(s: String) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show()

    }
}