package com.example.dishdash

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    const val base_url="https://api.spoonacular.com/"

   val instance:RecipeService by lazy{
       val retrofit=Retrofit.Builder().baseUrl(
        "https://api.spoonacular.com/"

       ).addConverterFactory(GsonConverterFactory.create()).build()
       retrofit.create(RecipeService::class.java)
   }
}