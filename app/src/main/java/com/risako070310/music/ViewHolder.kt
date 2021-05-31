package com.risako070310.music

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val nameView: TextView = itemView.findViewById(R.id.nameContentView)
    val songView: TextView = itemView.findViewById(R.id.songContentView)
    val artistView: TextView = itemView.findViewById(R.id.artistNameView)
    val commentView: TextView = itemView.findViewById(R.id.commentView)
    val imageView: ImageView = itemView.findViewById(R.id.jacketContentView)
}