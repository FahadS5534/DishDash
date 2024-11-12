package com.example.dishdash

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchActivity : AppCompatActivity() {
    lateinit var editText: EditText
    lateinit var searchAdapter: SearchAdapter
    lateinit var recyclerView: RecyclerView

    private fun fetchDataFromSearch(query: String) {
        val apiKey = "ff046b9c790046358cde6252d98f668a"
        val call = RetrofitClient.instance.getRecipeBySearch(apiKey, query, 7)
        call.enqueue(object : Callback<RecipeSearchResponse> {
            override fun onResponse(
                call: Call<RecipeSearchResponse>,
                response: Response<RecipeSearchResponse>
            ) {
                if (response.isSuccessful) {
                    val recipe: List<RecipeSearch> = response.body()?.results ?: emptyList()
                    val layoutManager =
                        StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
                    recyclerView.layoutManager = layoutManager
                    searchAdapter = SearchAdapter(this@SearchActivity, recipe, apiKey)
                    recyclerView.adapter = searchAdapter
                } else {
                    Log.e("SearchActivity", "Error fetching recipes: ${response.errorBody()}")
                    println("Error: ${response.errorBody()?.string() ?: "Unknown error"}")
                }
            }

            override fun onFailure(call: Call<RecipeSearchResponse>, t: Throwable) {
                Log.e("SearchActivity", "Error fetching recipes: ${t.message}", t)
            }
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_search)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        editText = findViewById(R.id.searchEdittext)
        recyclerView = findViewById(R.id.searchRecycler)


        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Do nothing
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Perform search when text changes
                s?.let {
                    if (it.length >= 3) { // Only search if the query is at least 3 characters long
                        fetchDataFromSearch(it.toString())
                    }
                }
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })

        // Alternatively, you can trigger search on editor action (like pressing enter)
        editText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                fetchDataFromSearch(editText.text.toString())
                true
            } else {
                false
            }
        }
    }
}
