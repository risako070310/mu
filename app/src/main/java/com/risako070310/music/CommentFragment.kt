package com.risako070310.music

import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_comment.*
import java.time.LocalDateTime

class CommentFragment : Fragment() {

    private val db = Firebase.firestore
    private val songId = arguments?.getString("songId")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_comment, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val song = "はしりがき"
        val artist = "マカロニえんぴつ"
        val imageURL = "https://i.scdn.co/image/ab67616d00001e02651f95448e23830d42f141eb"
        val spotifyURL = "https://open.spotify.com/track/4rVpueOWFM9jUjZSWt1eNu?si=g19PvA_8SpSTxJYkXif_-Q"

        nextButton.setOnClickListener {
            val user = hashMapOf(
                "user-name" to arguments?.getString("name"),
                "song" to song,
                "artist" to artist,
                "message" to commentEditText.text.toString(),
                "imageURL" to imageURL,
                "spotifyURL" to spotifyURL,
                "updateTime" to LocalDateTime.now()
            )

            db.collection("users")
                .add(user)
                .addOnSuccessListener { document ->
                    val userData: SharedPreferences = requireActivity().getSharedPreferences(
                        "userId",
                        AppCompatActivity.MODE_PRIVATE
                    )
                    val editor = userData.edit()
                    editor.putString("user", document.id)
                    editor.apply()
                    Toast.makeText(this.context, "Firebaseへの保存成功", Toast.LENGTH_SHORT).show()

                    val intent = Intent(this.context, MainActivity::class.java)
                    startActivity(intent)
                }.addOnFailureListener {
                    Toast.makeText(this.context, "失敗", Toast.LENGTH_SHORT).show()
                }
        }
    }

}