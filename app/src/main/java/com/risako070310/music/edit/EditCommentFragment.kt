package com.risako070310.music.edit

import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.jakewharton.threetenabp.AndroidThreeTen
import com.risako070310.music.main.MainActivity
import com.risako070310.music.databinding.FragmentCommentBinding
import org.threeten.bp.LocalDateTime
import org.threeten.bp.format.DateTimeFormatter

class EditCommentFragment : Fragment() {

    lateinit var binding: FragmentCommentBinding
    private val db = Firebase.firestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCommentBinding.inflate(layoutInflater)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
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
                if (user != null && user.data != null) {
                    binding.commentEditText.setText(user.data?.get("comment").toString())
                }

            }

        AndroidThreeTen.init(this.context)

        binding.nextButton.setOnClickListener {
            val localDateTime = LocalDateTime.now()
            val dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")
            val user = hashMapOf(
                "name" to arguments?.getString("name"),
                "song" to arguments?.getString("song"),
                "artist" to arguments?.getString("artist"),
                "comment" to binding.commentEditText.text.toString(),
                "imageURL" to arguments?.getString("imageUrl"),
                "songURL" to arguments?.getString("songUrl"),
                "updateTime" to localDateTime.format(dtf)

            )

            db.collection("users").document(userId).set(user)
                .addOnSuccessListener { _ ->
                    Toast.makeText(this.context, "更新しました", Toast.LENGTH_SHORT).show()

                    val intent = Intent(this.context, MainActivity::class.java)
                    startActivity(intent)
                    requireActivity().finish()
                }.addOnFailureListener {
                    Toast.makeText(this.context, "更新失敗", Toast.LENGTH_SHORT).show()
                }
        }
    }
}