package com.risako070310.music

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import coil.api.load
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_list.*
import kotlinx.android.synthetic.main.fragment_list.view.*


class ListFragment : Fragment() {

    private lateinit var viewAdapter :FirestoreRecyclerAdapter<User, ViewHolder>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val query = FirebaseFirestore.getInstance()
            .collection("users")
            .orderBy("updateTime")

        val options = FirestoreRecyclerOptions.Builder<User>()
            .setQuery(query, User::class.java)
            .build()

        viewAdapter =
            object : FirestoreRecyclerAdapter<User, ViewHolder>(options) {
                override fun onBindViewHolder(holder: ViewHolder, position: Int, model: User) {
                    holder.nameView.text = model.name
                    holder.songView.text = model.song
                    holder.artistView.text = model.artist
                    holder.commentView.text = model.comment
                    holder.imageView.load(model.imageURL)
                }

                override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
                    val listView = LayoutInflater.from(parent.context).inflate(R.layout.item_list_cell, parent, false)
                    return ViewHolder(listView)
                }
            }

        view.recyclerView.apply{
            adapter = viewAdapter
            layoutManager = LinearLayoutManager(this.context)
        }
    }

    override fun onStart() {
        super.onStart()
        if(::viewAdapter.isInitialized){
            viewAdapter.startListening()
        }
    }
}