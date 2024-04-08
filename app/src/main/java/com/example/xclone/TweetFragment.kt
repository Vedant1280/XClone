package com.example.xclone

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.xclone.Adapters.TweetAdapter
import com.example.xclone.data.SuggestedAccount
import com.example.xclone.data.Tweet
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class TweetFragment : Fragment() {
    private lateinit var tweetAdapter: TweetAdapter
    private lateinit var rvTweet: RecyclerView
    private val listOfTweet= mutableListOf<Tweet>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view=inflater.inflate(R.layout.fragment_tweet,container,false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvTweet=view.findViewById(R.id.rv_tweets)
        FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().uid.toString())
            .addListenerForSingleValueEvent(object:ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    val listOfFollowinguids=snapshot.child("listOfFollowings").value as MutableList<String>
                    listOfFollowinguids.add(FirebaseAuth.getInstance().uid.toString())
                    listOfFollowinguids.forEach {
                        getTweetFromUID(it)
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })
    }

    private fun getTweetFromUID(uid : String)
    {
        FirebaseDatabase.getInstance().getReference().child("users").child(uid)
            .addListenerForSingleValueEvent(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    
                    var tweetList = mutableListOf<String>()
                    snapshot.child("listOfTweets").value?.let {
                        tweetList = it as MutableList<String>
                    }
                    tweetList.forEach { tweetContent ->
                        if (!tweetContent.isNullOrBlank()) {
                            // Fetch profile image and account user name from the user's data
                            val profileImage = snapshot.child("userProfileImage").value.toString()
                            val accountUserName = snapshot.child("userName").value.toString()

                            listOfTweet.add(Tweet(tweetContent, profileImage, accountUserName))
                        }
                    }
                    tweetAdapter = TweetAdapter(listOfTweet, requireContext())
                    rvTweet.layoutManager = LinearLayoutManager(requireContext())
                    rvTweet.adapter = tweetAdapter
                }

                override fun onCancelled(error: DatabaseError) {
                    // Handle error
                }
            })
    }

}