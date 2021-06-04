package com.risako070310.music.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.risako070310.music.recycler.ListAdapter
import com.risako070310.music.recycler.ListViewHolder
import com.risako070310.music.R
import com.risako070310.music.dataclass.User


class ListFragment : Fragment() {

    private lateinit var viewAdapter: FirestoreRecyclerAdapter<User, ListViewHolder>
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

        viewAdapter = ListAdapter(options)

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