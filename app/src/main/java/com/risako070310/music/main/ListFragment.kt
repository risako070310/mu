package com.risako070310.music.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.risako070310.music.recycler.ListAdapter
import com.risako070310.music.recycler.ListViewHolder
import com.risako070310.music.databinding.FragmentListBinding
import com.risako070310.music.dataclass.User


class ListFragment : Fragment() {

    lateinit var binding: FragmentListBinding

    private lateinit var viewAdapter: FirestoreRecyclerAdapter<User, ListViewHolder>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val query = FirebaseFirestore.getInstance()
            .collection("users")
            .orderBy("updateTime", Query.Direction.DESCENDING)

        val options = FirestoreRecyclerOptions.Builder<User>()
            .setQuery(query, User::class.java)
            .build()

        viewAdapter = ListAdapter(options)

        binding.recyclerView.apply {
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