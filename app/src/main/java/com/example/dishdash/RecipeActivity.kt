package com.example.dishdash

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlin.random.Random

class RecipeActivity : AppCompatActivity() {
    lateinit var recipe: RandomRecipe
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_recipe_activiity)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        recipe = intent.getSerializableExtra("recipeById") as? RandomRecipe
            ?: intent.getSerializableExtra("recipe") as? RandomRecipe
                    ?: run {
                Toast.makeText(this, "Error loading recipe", Toast.LENGTH_SHORT).show()
                return
            }

        var title: TextView = findViewById(R.id.title)
        var type: TextView = findViewById(R.id.type)
        var servings:TextView=findViewById(R.id.servings)
        var rating: TextView = findViewById(R.id.searchratingTextView)
        var time: TextView = findViewById(R.id.time)
        var cal: TextView = findViewById(R.id.cals)
        var difficulty: TextView = findViewById(R.id.difficulty)
        var imageView: ImageView = findViewById(R.id.imageView2)
        val recyclerView:RecyclerView=findViewById(R.id.recyclerView2)

        if(recipe==null){
            Toast.makeText(this@RecipeActivity,"error",Toast.LENGTH_SHORT).show()
        }
        Picasso.get().load(recipe.image).into(imageView)
        title.text = recipe.title;
        type.text = recipe.types.joinToString(separator = ",")
        rating.text = getRandomRating()
        servings.text=recipe.servings.toString()
        time.text=recipe.time.toString()
        cal.text=getRandomCalories()
        difficulty.text=getDifficulty(recipe.time)
        val adapter=IngredientsAdapter(recipe.ingredients)
        val linearLayoutManager=LinearLayoutManager(this)
        linearLayoutManager.orientation=LinearLayoutManager.VERTICAL
      recyclerView.layoutManager=linearLayoutManager
        recyclerView.adapter=adapter
    }

    private fun getDifficulty(time: Int): CharSequence{
        return if(time<=30){
            "Easy"
        } else if(time<=60){
            "Medium"
        } else{
            "Hard"
        }
    }

    private fun getRandomCalories():CharSequence{
        // Generate a random integer between 99 and 500 (inclusive)
        return Random.nextInt(99, 501).toString()
    }

    private fun getRandomRating(): String {
        val ratings = arrayOf("3.0", "3.5", "3.6", "3.8", "4.1", "4.5", "4.8", "5.0")
        val randomIndex = (Math.random() * ratings.size).toInt()
        return "     ${ratings[randomIndex]}"
    }
}
