package com.example.dishdash

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso

class RandomRecipeAdapter(val context: Context, private val list: List<RandomRecipe>) : RecyclerView.Adapter<RandomRecipeAdapter.RandomRecipeViewHolder>() {

    class RandomRecipeViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        val title: TextView = item.findViewById(R.id.searchtitleTextView)
        val time: TextView = item.findViewById(R.id.timeTextView)
        val image: ImageView = item.findViewById(R.id.isearchmageView)
        val card: CardView = item.findViewById(R.id.card)
        val bookmark: ImageView = item.findViewById(R.id.bookmarkImageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RandomRecipeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_recipe_card, parent, false)
        return RandomRecipeViewHolder(view)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: RandomRecipeViewHolder, position: Int) {
        val recipe = list[position]
        holder.title.text = recipe.title
        holder.time.text = recipe.time.toString()
        Picasso.get().load(recipe.image).into(holder.image)

        holder.card.setOnClickListener {
            val intent = Intent(context, RecipeActivity::class.java)
            intent.putExtra("recipe", recipe)
            context.startActivity(intent)
        }

        val currentUser = FirebaseAuth.getInstance().currentUser
        currentUser?.let { user ->
            val colRef = FirebaseFirestore.getInstance().collection("userData").document(currentUser.uid).collection("bookmarks")


            colRef.document(recipe.title).get()
                .addOnSuccessListener { documentSnapshot ->
                    if (documentSnapshot.exists()) {

                        holder.bookmark.setBackgroundResource(R.drawable.bookmark_saved)
                    } else {

                        holder.bookmark.setBackgroundResource(R.drawable.baseline_bookmark_border_24)
                    }
                }
                .addOnFailureListener { exception ->
                    Toast.makeText(context, "Failed to check bookmark status: ${exception.message}", Toast.LENGTH_SHORT).show()
                }


            holder.bookmark.setOnClickListener {
                colRef.document(recipe.title).get()
                    .addOnSuccessListener { documentSnapshot ->
                        if (documentSnapshot.exists()) {


                            colRef.document(recipe.title).delete()
                                .addOnSuccessListener {
                                    Toast.makeText(context, "Bookmark removed", Toast.LENGTH_SHORT).show()
                                    holder.bookmark.setBackgroundResource(R.drawable.baseline_bookmark_border_24)
                                }
                                .addOnFailureListener { exception ->
                                    Toast.makeText(context, "Failed to remove bookmark: ${exception.message}", Toast.LENGTH_SHORT).show()
                                }
                        } else{

                            colRef.document(recipe.title).set(recipe)
                                .addOnSuccessListener {
                                    Toast.makeText(context, "Recipe bookmarked", Toast.LENGTH_SHORT).show()
                                    holder.bookmark.setBackgroundResource(R.drawable.bookmark_saved)
                                }
                                .addOnFailureListener { exception ->
                                    Toast.makeText(context, "Failed to bookmark recipe: ${exception.message}", Toast.LENGTH_SHORT).show()
                                }
                        }
                    }
                    .addOnFailureListener { exception ->
                        Toast.makeText(context, "Failed to check bookmark status: ${exception.message}", Toast.LENGTH_SHORT).show()
                    }
            }
        }
    }
}
