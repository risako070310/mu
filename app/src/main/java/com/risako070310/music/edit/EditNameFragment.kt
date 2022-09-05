package com.risako070310.music.edit

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.risako070310.music.R
import com.risako070310.music.databinding.FragmentNameBinding

class EditNameFragment : Fragment(){

    lateinit var binding: FragmentNameBinding

    val db = Firebase.firestore

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNameBinding.inflate(layoutInflater)
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
                    binding.nameEditText.setText(user.data?.get("name").toString())
                }

            }

        binding.nextButton.setOnClickListener {
            val name = binding.nameEditText.text.toString()
            val bundle = bundleOf("name" to name)
            findNavController().navigate(R.id.edit_name_to_choose, bundle)
        }
    }
}