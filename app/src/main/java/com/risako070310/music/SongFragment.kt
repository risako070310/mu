package com.risako070310.music

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import coil.api.load
import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.fragment_song.*
import kotlinx.coroutines.runBlocking
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SongFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_song, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val songId = arguments?.getString("songId")

        val gson = GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .create()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.song.link/v1-alpha.1/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

        val musicService = retrofit.create(MusicService::class.java)
        runBlocking{
            runCatching {
                musicService.getMusic(songId!!)
            }
        }.onSuccess {
            songTitle.text = it.title
            artistName.text = it.artist
            jacketView.load(it.imageURL)
        }.onFailure {
            Toast.makeText( context, "失敗してるよ？？？？？？？", Toast.LENGTH_LONG).show()
        }

        nextButton.setOnClickListener {
            val bundle = bundleOf("name" to arguments?.getString("name"), "songId" to "")
            findNavController().navigate(R.id.song_to_comment, bundle)
        }
    }
}
