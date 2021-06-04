package com.risako070310.music.recycler

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import coil.api.load
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.risako070310.music.R
import com.risako070310.music.dataclass.User

class ListAdapter internal constructor(options: FirestoreRecyclerOptions<User>) :
    FirestoreRecyclerAdapter<User, ListViewHolder>(options) {

    override fun onBindViewHolder(holder: ListViewHolder, position: Int, model: User) {
        holder.nameView.text = model.name
        holder.songView.text = model.song
        holder.artistView.text = model.artist
        holder.commentView.text = model.comment
        holder.imageView.load(model.imageURL)
        holder.itemView.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(model.songURL))
            it.context.startActivity(intent)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val listView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_list_cell, parent, false)
        return ListViewHolder(listView)
    }
}