package com.example.dishdash

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class IngredientsAdapter(val list:List<Ingredients>):RecyclerView.Adapter<IngredientsAdapter.IngredientsViewHolder>() {
    class IngredientsViewHolder(item: View) :RecyclerView.ViewHolder(item){
        val title:TextView=item.findViewById(R.id.IngredientsTitle)
        val unit:TextView=item.findViewById(R.id.Unit)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientsViewHolder {
      val view=LayoutInflater.from(parent.context).inflate(R.layout.ingredients_item,parent,false)
        return IngredientsViewHolder(view)
    }

    override fun getItemCount(): Int {
    return  list.size
    }

    override fun onBindViewHolder(holder: IngredientsViewHolder, position: Int) {
     val ingredient=list[position]
        holder.title.text=ingredient.name
        holder.unit.text=ingredient.amount.toString()
    }
}