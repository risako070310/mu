package com.risako070310.music

import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import coil.api.load
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_user.*

class UserFragment : Fragment() {

    val db = Firebase.firestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_user, container, false)
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
                    nameView.text = user.data?.get("name").toString()
                    songView.text = user.data?.get("song").toString()
                    artistView.text = user.data?.get("artist").toString()
                    songImageView.load(user.data!!["imageURL"].toString())
                    commentText.text = user.data?.get("comment").toString()
                }

            }
    }

}