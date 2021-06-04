package com.risako070310.music.recycler

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.risako070310.music.R

class ResultViewHolder(view: View) : RecyclerView.ViewHolder(view){

    interface ItemClickListener {
        fun onItemClick(view: View, position: Int)
    }

    val songView: TextView = view.findViewById(R.id.resultSongName)
    val artistView: TextView = view.findViewById(R.id.resultArtistName)
    val imageView: ImageView = view.findViewById(R.id.resultImageView)
}