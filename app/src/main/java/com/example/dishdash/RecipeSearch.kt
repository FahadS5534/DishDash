package com.example.dishdash

import com.google.gson.annotations.SerializedName

data class RecipeSearch(
    @SerializedName("id")
    val id:Int,
    @SerializedName("title")
    val title:String,
    @SerializedName("image")
    val image:String
)
data class RecipeSearchResponse(
    val results:List<RecipeSearch>
)
