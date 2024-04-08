package com.example.xclone

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.xclone.data.User
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.database

class LoginActivity : AppCompatActivity() {
    private lateinit var edtEmail : EditText
    private lateinit var edtUserName:EditText
    private lateinit var edtPassword : EditText
    private lateinit var btnLogin : Button
    private lateinit var btnSignup : Button
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        init()

        if(auth.currentUser!=null){
            val intent=Intent(this,HomeActivity::class.java)
            startActivity(intent)
            //destroy the login activity
            finish()
        }

        btnLogin.setOnClickListener {
            val Email=edtEmail.text.toString()
            val Password=edtPassword.text.toString()
            login(Email, Password)
        }

        btnSignup.setOnClickListener {
            val Email=edtEmail.text.toString()
            val Password=edtPassword.text.toString()
            signup(Email, Password)
        }
    }

    private fun login(Email: String,Password : String)
    {
        auth.signInWithEmailAndPassword(Email,Password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                   val intent=Intent(this,HomeActivity::class.java)
                    startActivity(intent)
                    //destroy the login activity
                    finish()

                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(this, "some error occured", LENGTH_LONG).show()
                }
            }
    }

    private fun signup(Email: String,Password : String)
    {
        auth.createUserWithEmailAndPassword(Email,Password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {

                    val listOfFollowings= mutableListOf<String>()
                    listOfFollowings.add("")
                    val listOfTweets=mutableListOf<String>()
                    listOfTweets.add("")
                    val Handle=edtUserName.text.toString()
                     //add user to firebase database
                    val user= User(
                        userName=Handle,
                        userEmail = Email,
                        userProfileImage = "",
                        listOfFollowings =listOfFollowings,
                        listOfTweets=listOfTweets,
                        uid=auth.uid.toString()
                    )

                    addUserToDatabase(user)

                    val intent=Intent(this,HomeActivity::class.java)
                    startActivity(intent)
                    //destroy the login activity
                    finish()

                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(this, "some error occured", LENGTH_LONG).show()
                }
            }
    }

    private fun addUserToDatabase(user:User)
    {
      Firebase.database.getReference("users").child(user.uid).setValue(user)
    }
    private fun init()
    {
        edtEmail=findViewById(R.id.email_edittext)
        edtUserName=findViewById(R.id.username_edittext)
        edtPassword=findViewById(R.id.password_edittext)
        btnLogin=findViewById(R.id.login_btn)
        auth= Firebase.auth
        btnSignup=findViewById(R.id.register_button)
    }
}