package com.risako070310.music

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query


class ListFragment : Fragment() {

    private lateinit var viewAdapter: FirestoreRecyclerAdapter<User, ViewHolder>
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.recyclerView)

        val query = FirebaseFirestore.getInstance()
            .collection("users")
            .orderBy("updateTime", Query.Direction.DESCENDING)

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
                    val listView = LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_list_cell, parent, false)
                    return ViewHolder(listView)
                }
            }

        recyclerView.apply {
            adapter = viewAdapter
            layoutManager = LinearLayoutManager(this.context)
        }
    }

    override fun onStart() {
        super.onStart()
        if (::viewAdapter.isInitialized) {
            viewAdapter.startListening()
        }
    }
}