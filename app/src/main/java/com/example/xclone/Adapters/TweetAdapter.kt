package com.example.xclone.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.xclone.R
import com.example.xclone.data.SuggestedAccount
import com.example.xclone.data.Tweet
import de.hdodenhof.circleimageview.CircleImageView


class TweetAdapter(
    private val listOfTweet: List<Tweet>,
    private val context: Context
 )  : RecyclerView.Adapter<TweetAdapter.viewHolder>() {

    class viewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: CircleImageView = itemView.findViewById(R.id.tweet_image)
        val textTweet: TextView = itemView.findViewById(R.id.tweet_text)
        val userName: TextView = itemView.findViewById(R.id.user_name)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_tweet, parent, false)
        return viewHolder(view)
    }

    override fun getItemCount(): Int {
        return listOfTweet.size
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        val currentTweet=listOfTweet[position]
        holder.textTweet.text=currentTweet.tweetContent
        Glide.with(context)
            .load(currentTweet.profileImage)
            .into(holder.image)
        holder.userName.text=currentTweet.accountUserName
        }
    }
