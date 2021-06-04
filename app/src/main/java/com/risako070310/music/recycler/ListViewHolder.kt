package com.risako070310.music.recycler

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.risako070310.music.R

class ListViewHolder internal constructor(view: View) : RecyclerView.ViewHolder(view) {
    val nameView: TextView = view.findViewById(R.id.nameContentView)
    val songView: TextView = view.findViewById(R.id.songContentView)
    val artistView: TextView = view.findViewById(R.id.artistNameView)
    val commentView: TextView = view.findViewById(R.id.commentView)
    val imageView: ImageView = view.findViewById(R.id.jacketContentView)
}