package com.risako070310.music.login

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
import com.jakewharton.threetenabp.AndroidThreeTen
import com.risako070310.music.main.MainActivity
import com.risako070310.music.R
import kotlinx.android.synthetic.main.fragment_comment.*
import org.threeten.bp.LocalDateTime
import org.threeten.bp.format.DateTimeFormatter

class CommentFragment : Fragment() {

    private val db = Firebase.firestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_comment, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        AndroidThreeTen.init(this.context)

        nextButton.setOnClickListener {
            val localDateTime = LocalDateTime.now()
            val dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")
            val user = hashMapOf(
                "name" to arguments?.getString("name"),
                "song" to arguments?.getString("song"),
                "artist" to arguments?.getString("artist"),
                "comment" to commentEditText.text.toString(),
                "imageURL" to arguments?.getString("imageUrl"),
                "songURL" to arguments?.getString("songUrl"),
                "updateTime" to localDateTime.format(dtf)
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
                    Toast.makeText(this.context, "アカウントが作成されました", Toast.LENGTH_SHORT).show()

                    val intent = Intent(this.context, MainActivity::class.java)
                    startActivity(intent)
                    requireActivity().finish()
                }.addOnFailureListener {
                    Toast.makeText(this.context, "失敗", Toast.LENGTH_SHORT).show()
                }
        }
    }

}