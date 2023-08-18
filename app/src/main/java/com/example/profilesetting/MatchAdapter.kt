package com.example.profilesetting

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class MatchAdapter(private var userList: List<UserProfile>) :
    RecyclerView.Adapter<MatchAdapter.MyViewHolder> (){



    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        // declared teh variable from each_item
        var RvName : TextView
        var profileImage : ImageView
        var RvAddress : TextView




        // Initializing the declared variable
        init {
            RvName = itemView.findViewById(R.id.itemName)
            profileImage = itemView.findViewById(R.id.itemImage)
            RvAddress = itemView.findViewById(R.id.itemAddress)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newList: List<UserProfile>) {
        userList = newList
        notifyDataSetChanged() // Notify the adapter of the data change
    }




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatchAdapter.MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_match, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MatchAdapter.MyViewHolder, position: Int) {

        val currentUser = userList[position]
        holder.RvName.text = currentUser.name
        holder.RvAddress.text = currentUser.address
        if (currentUser.imageUrl.isEmpty()) {

            // User doesn't have a profile image, use default
            Picasso.get()
                .load(R.drawable.photo)
                .into(holder.profileImage)

        } else {

            // User has profile image, load it
            Picasso.get()
                .load(currentUser.imageUrl)
                .into(holder.profileImage)

        }

    }

    override fun getItemCount(): Int {
        return userList.size
    }

}