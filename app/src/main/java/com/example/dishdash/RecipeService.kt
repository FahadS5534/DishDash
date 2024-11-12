package com.example.dishdash

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RecipeService {
    @GET("recipes/random")
    fun getRandomRecipe(
        @Query("apiKey") apiKey:String,
        @Query("number") number:Int=10
    ): Call<RandomRecipeResponse>
    @GET("recipes/complexSearch")
    fun getRecipeBySearch(
        @Query("apiKey") apiKey: String,
        @Query("query") query:String,
        @Query("number") number: Int=5
    ):Call<RecipeSearchResponse>
    @GET("recipes/{id}/information")
    fun getRecipeInformation(
        @Path("id") id: Int,
        @Query("apiKey") apiKey: String
    ): Call<RandomRecipe>

}