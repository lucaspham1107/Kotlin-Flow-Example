package com.digalyst.myapplication.base

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.digalyst.myapplication.R
import com.digalyst.myapplication.data.model.ApiUser

class ApiUserAdapter(
    private val users: ArrayList<ApiUser>
) : RecyclerView.Adapter<ApiUserAdapter.DataViewHolder>() {

    class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(user: ApiUser) {
            itemView.findViewById<AppCompatTextView>(R.id.textViewUserName).text = user.name
            itemView.findViewById<AppCompatTextView>(R.id.textViewUserEmail).text = user.email
            Glide.with(itemView.findViewById<ImageView>(R.id.imageViewAvatar).context)
                .load(user.avatar)
                .into(itemView.findViewById<ImageView>(R.id.imageViewAvatar))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        DataViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_layout, parent,
                false
            )
        )

    override fun getItemCount(): Int = users.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) =
        holder.bind(users[position])

    fun addData(list: List<ApiUser>) {
        users.addAll(list)
    }

}