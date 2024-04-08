package com.example.xclone

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager2.widget.ViewPager2
import com.example.xclone.Adapters.VPAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class HomeActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var fabTweet: FloatingActionButton
    private lateinit var vpAdapter: VPAdapter
    private lateinit var viewPager : ViewPager2
    private lateinit var tabLayout:TabLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home)
         init()
        TabLayoutMediator(tabLayout,viewPager){ tab:TabLayout.Tab , position:Int->
            when(position){
                0->tab.text="Accounts"
                else -> tab.text="Tweets"
            }

        }.attach()

        fabTweet.setOnClickListener{
            val intent=Intent(this,TweetActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        super.onOptionsItemSelected(item)

        when(item.itemId){
            R.id.menu_logout->{
                  //logout user
                Toast.makeText(this,"Log out clicked",Toast.LENGTH_LONG).show()
                auth.signOut()
                val intent= Intent(this,LoginActivity::class.java)
                startActivity(intent)
                //destroy the login activity
                finish()
            }else->{
              //open profile
            Toast.makeText(this,"Profile clicked",Toast.LENGTH_LONG).show()
            val intent= Intent(this,ProfileActivity::class.java)
            startActivity(intent)
            }
        }
        return true
    }

    private fun init()
    {
        auth=Firebase.auth
        fabTweet=findViewById(R.id.fab_tweet)
        vpAdapter= VPAdapter(this)
        viewPager=findViewById(R.id.view_pager)
        viewPager.adapter=vpAdapter
        tabLayout=findViewById(R.id.tab_layout)
    }
}