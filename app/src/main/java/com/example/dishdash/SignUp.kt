package com.example.dishdash

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class SignUp : AppCompatActivity() {

     lateinit var  auth:FirebaseAuth
     lateinit var SignUp:Button
     lateinit var user:User
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sign_up)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val login: TextView =findViewById(R.id.navigateToLogin)
        SignUp=findViewById(R.id.SignUp)

        //firebase
       val email:EditText=findViewById(R.id.email)
        val password:EditText=findViewById(R.id.password_signup)
        val name:EditText=findViewById(R.id.name)



       SignUp.setOnClickListener{
            if(email.text.isNullOrEmpty()){
                email.setBackgroundResource(R.drawable.edit_text_error)
                email.error="Please enter a valid email address"

            }
            else if (password.text.isNullOrEmpty()){

             password.setBackgroundResource(R.drawable.edit_text_error)
                password.error="please enter a valid password"
            }
            else if(name.text.isNullOrEmpty()){

               name.setBackgroundResource(R.drawable.edit_text_error)
                name.error="please enter a valid name"
            }
            else{
                email.setBackgroundResource(R.drawable.edit_text_background)
               password.setBackgroundResource(R.drawable.edit_text_background)
               name.setBackgroundResource(R.drawable.edit_text_background)

                auth=FirebaseAuth.getInstance()
                auth.createUserWithEmailAndPassword(email.text.toString(),password.text.toString()).addOnCompleteListener{
                    task->if(task.isSuccessful){
                        val fuser=auth.currentUser
                    fuser?.sendEmailVerification()?.addOnCompleteListener{

                       Toast.makeText(this,"Email verification is sent please verify!!",Toast.LENGTH_SHORT).show()
                        user =User(name.text.toString())
                        FirebaseFirestore.getInstance().collection("Users").add(user)
                        val intent= Intent(this,MainActivity::class.java)
                        startActivity(intent)
                    }

                }
                    else{
                    Toast.makeText(this,"There was a problem Creating a user please try again",Toast.LENGTH_SHORT).show()
                }
                }
            }

        }
        login.setOnClickListener{
            val intent= Intent(this,MainActivity::class.java)
            startActivity(intent)
        }



    }
}