package com.risako070310.music

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.widget.doOnTextChanged
import androidx.navigation.fragment.findNavController
import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.fragment_choose.*
import kotlinx.coroutines.runBlocking
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ChooseFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_choose, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val gson = GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .create()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.spotify.com")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

        val musicService = retrofit.create(MusicSearch::class.java)

        songEditText.doOnTextChanged { text, _, _, _ ->
            runBlocking{
                runCatching {
                    musicService.searchMusic(text!!.toString())
                }.onSuccess{
                    Log.d("result",it.toString())
                }.onFailure {
                    Log.d("error", it.message.toString())
                }
            }
        }

//        nextButton.setOnClickListener {
////            val songName = songEditText.text.toString()
//            val songId = songEditText.text.toString().substring(31, 53)
//            text.text = songId
//            val bundle = bundleOf("name" to arguments?.getString("name"), "songId" to songId)
//            findNavController().navigate(R.id.choose_to_song, bundle)
//        }
    }
}