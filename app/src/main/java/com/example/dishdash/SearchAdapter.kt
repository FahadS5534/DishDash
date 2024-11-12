package com.example.dishdash

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchAdapter(
    val context: Context,
    val list: List<RecipeSearch>,
    val apiKey: String
) : RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {

    class SearchViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        val title: TextView = item.findViewById(R.id.searchtitleTextView)
        val rating: TextView = item.findViewById(R.id.searchratingTextView)
        val image: ImageView = item.findViewById(R.id.isearchmageView)
        val card: CardView = item.findViewById(R.id.card)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.search_recipe_card, parent, false)
        return SearchViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val recipe = list[position]
        holder.title.text = recipe.title
        Picasso.get().load(recipe.image).into(holder.image)
        holder.rating.text = getRandomRating()

        holder.card.setOnClickListener {
            fetchDataById(recipe) { detailedRecipe ->
                detailedRecipe?.let {
                    val intent = Intent(context, RecipeActivity::class.java).apply {
                        putExtra("recipeById", it)
                    }
                    context.startActivity(intent)
                }
            }
        }
    }

    private fun fetchDataById(recipe: RecipeSearch, callback: (RandomRecipe?) -> Unit) {
        val call = RetrofitClient.instance.getRecipeInformation(recipe.id, apiKey)
        call.enqueue(object : Callback<RandomRecipe> {
            override fun onResponse(call: Call<RandomRecipe>, response: Response<RandomRecipe>) {
                if (response.isSuccessful) {
                    val detailedRecipe: RandomRecipe? = response.body()
                    callback(detailedRecipe)
                } else {
                    callback(null)
                }
            }

            override fun onFailure(call: Call<RandomRecipe>, t: Throwable) {
                callback(null)
            }
        })
    }

    private fun getRandomRating(): String {
        val ratings = arrayOf("3.0", "3.5", "3.6", "3.8", "4.1", "4.5", "4.8", "5.0")
        val randomIndex = (Math.random() * ratings.size).toInt()
        return "  ${ratings[randomIndex]}"
    }
}
