package com.risako070310.music.recycler

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.risako070310.music.R
import com.risako070310.music.dataclass.Data

class ResultAdapter(
    private val context: Context,
    private val itemClickListener: ResultViewHolder.ItemClickListener,
    private val data: Data
) :
    RecyclerView.Adapter<ResultViewHolder>() {

    private var resultView : RecyclerView? = null

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        resultView = recyclerView
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        resultView = null

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.result_list_cell, parent, false)
        view.setOnClickListener{
            resultView?.let{
                itemClickListener.onItemClick(view, it.getChildAdapterPosition(view))
            }
        }
        return ResultViewHolder(view)
    }

    override fun onBindViewHolder(holder: ResultViewHolder, position: Int) {
        holder.let {
            it.songView.text = data.trackData.items[position].name
            it.artistView.text = data.trackData.items[position].album.artist[0].name
            it.imageView.load(data.trackData.items[position].album.images[0].imageUrl)
        }
    }

    override fun getItemCount(): Int {
        return data.hashCode()
    }
}