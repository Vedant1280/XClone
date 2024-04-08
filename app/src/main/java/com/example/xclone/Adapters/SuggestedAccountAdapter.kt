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
import de.hdodenhof.circleimageview.CircleImageView

class SuggestedAccountAdapter(
    private val listOfAccounts: List<SuggestedAccount>,
    private val context: Context,
private val clickListener: ClickListener)  : RecyclerView.Adapter<SuggestedAccountAdapter.viewHolder>() {

    class viewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        val image:CircleImageView=itemView.findViewById(R.id.suggested_account_profile)
        val username:TextView=itemView.findViewById(R.id.text_suggested_account_username)
        val btnFollow:Button=itemView.findViewById(R.id.btn_follow)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
       val view=LayoutInflater.from(parent.context).inflate(R.layout.layout_suggested_account,parent,false)
       return viewHolder(view)
    }

    override fun getItemCount(): Int {
      return listOfAccounts.size
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        val currentAccount =listOfAccounts[position]
        holder.username.text=currentAccount.userName
        Glide.with(context)
            .load(currentAccount.profileImage)
            .into(holder.image)

        holder.btnFollow.setOnClickListener{
           clickListener.onFollowClicked(currentAccount.uid)
        }
    }

    interface ClickListener{
        fun onFollowClicked(uid:String)
    }
}