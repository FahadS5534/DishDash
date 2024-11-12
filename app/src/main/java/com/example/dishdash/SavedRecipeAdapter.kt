package com.example.dishdash

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class SavedRecipeAdapter(val list: List<RandomRecipe>,val context:Context):RecyclerView.Adapter<SavedRecipeAdapter.SavedRecipeViewHolder>() {
    class SavedRecipeViewHolder( val item: View) :RecyclerView.ViewHolder(item){
        val title:TextView=item.findViewById(R.id.savedtitleTextView)
        val time:TextView=item.findViewById(R.id.savedTime)
        val bookmark:ImageView=item.findViewById(R.id.savedbookmark)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SavedRecipeViewHolder {
        val view:View=LayoutInflater.from(parent.context).inflate(R.layout.saved_recipe_card,parent,false)
        return SavedRecipeViewHolder(view)
    }

    override fun getItemCount(): Int {
return list.size
    }

    override fun onBindViewHolder(holder: SavedRecipeViewHolder, position: Int) {
     val recipe:RandomRecipe=list[position]
        holder.title.text=recipe.title
        holder.time.text="${recipe.time} Mins"
        holder.bookmark.setOnClickListener{
             FirebaseAuth.getInstance().currentUser?.let { it1 ->
                FirebaseFirestore.getInstance().collection("userData").document(
                    it1.uid).collection("bookmarks").document(recipe.title).delete()


            }

        }
    }
}