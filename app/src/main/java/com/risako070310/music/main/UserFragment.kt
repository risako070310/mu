package com.risako070310.music.main

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.risako070310.music.databinding.FragmentUserBinding
import com.risako070310.music.edit.EditActivity

class UserFragment : Fragment() {

    lateinit var binding: FragmentUserBinding

    val db = Firebase.firestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUserBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val userData: SharedPreferences = requireContext().getSharedPreferences(
            "userId",
            AppCompatActivity.MODE_PRIVATE
        )
        val userId = userData.getString("user", "hogehoge")

        db.collection("users").document(userId!!)
            .get()
            .addOnCompleteListener {
                val user = it.result
                if(user != null && user.data != null){
                    binding.nameView.text = user.data?.get("name").toString()
                    binding.songView.text = user.data?.get("song").toString()
                    binding.artistView.text = user.data?.get("artist").toString()
                    binding.songImageView.load(user.data!!["imageURL"].toString())
                    binding.commentText.text = user.data?.get("comment").toString()
                }

            }

        binding.editButton.setOnClickListener{
            val intent = Intent(this.context, EditActivity::class.java)
            startActivity(intent)
        }
    }

}