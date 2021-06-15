package com.risako070310.music.main

import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.risako070310.music.R
import kotlinx.android.synthetic.main.fragment_location.*
import kotlinx.coroutines.flow.merge

class LocationFragment : Fragment() {

    val db = Firebase.firestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_location, container, false)
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
                    if(user.data?.get("locationSwitch") == "true"){
                        locationSwitch.isChecked = true
                    }
                }
            }

        locationSwitch.setOnCheckedChangeListener { _, isChecked ->
            val updates = hashMapOf<String, Any>( "locationSwitch" to isChecked.toString())
            db.collection("users").document(userId).update(updates)
        }
    }
}

