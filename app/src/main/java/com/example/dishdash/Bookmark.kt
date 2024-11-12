package com.example.dishdash

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


class Bookmark : Fragment() {



   lateinit var recyclerView:RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        var list= mutableListOf<RandomRecipe>()
        super.onCreate(savedInstanceState)
        FirebaseAuth.getInstance().currentUser?.let {
            FirebaseFirestore.getInstance().collection("userData").document(
                it.uid).collection("bookmarks").get().addOnCompleteListener{
                  list=  it.result.toObjects(RandomRecipe::class.java)

            }

        }


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_bookmark, container, false)
    }
}