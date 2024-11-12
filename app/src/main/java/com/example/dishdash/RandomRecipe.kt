package com.example.dishdash

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class RandomRecipe(
    @SerializedName("title")
    val title:String,
    @SerializedName("readyInMinutes")
    val time:Int,
    @SerializedName("image")
    val image:String,
    @SerializedName("extendedIngredients")
val ingredients :List<Ingredients>,
@SerializedName("servings")
    val servings:Int,
    @SerializedName("vegetarian")
    val isVegetarian:Boolean,
    @SerializedName("dishTypes")
    val types:List<String>
):Serializable

data class Ingredients (
    val name:String,
    val amount:Float
):Serializable



data class RandomRecipeResponse(
    @SerializedName("recipes")
    val recipes:List<RandomRecipe>
):Serializable






