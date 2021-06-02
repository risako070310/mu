package com.risako070310.music

import android.os.Bundle
import android.util.Log
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
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SongFragment : Fragment() {

    private var token = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_song, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val songId = arguments?.getString("songId")

        val gson = GsonBuilder().create()

        val httpLogging = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        val httpClientBuilder = OkHttpClient.Builder().addInterceptor(httpLogging).build()

        val tokenRetrofit = Retrofit.Builder()
            .baseUrl("https://accounts.spotify.com/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(httpClientBuilder)
            .build()

        val requestToken = tokenRetrofit.create(TokenRequest::class.java)
        runBlocking{
            runCatching {
                requestToken.getToken("client_credentials")
            }.onSuccess{
                token = it.token
                Log.d("token-result", token)
            }.onFailure {
                Log.d("token-error", it.message.toString())
            }
        }

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.spotify.com/")
            .client(httpClientBuilder)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

        val musicService = retrofit.create(MusicGet::class.java)
        runBlocking{
            runCatching {
                musicService.getMusic("Bearer $token" ,songId!!)
            }
        }.onSuccess {
            Log.d("music-result", it.toString())
        }.onFailure {
            Toast.makeText( context, "失敗してるよ？？？？？？？", Toast.LENGTH_LONG).show()
        }

        nextButton.setOnClickListener {
            val bundle = bundleOf("name" to arguments?.getString("name"), "songId" to songId)
            findNavController().navigate(R.id.song_to_comment, bundle)
        }
    }
}
