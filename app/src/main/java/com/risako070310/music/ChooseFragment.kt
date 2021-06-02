package com.risako070310.music

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.fragment_choose.*
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class ChooseFragment : Fragment() {

    private var token = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_choose, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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

        val musicService = retrofit.create(MusicSearch::class.java)

        songEditText.doOnTextChanged { text, _, _, _ ->
            if(text != null) {
                runBlocking {
                    runCatching {
                        musicService.searchMusic("Bearer $token", text.toString(), "track", 5)
                    }.onSuccess {
                        Log.d("result", it.toString())
                    }.onFailure {
                        Log.d("error", it.message.toString())
                    }
                }
            }
        }

        nextButton.setOnClickListener {
            val bundle = bundleOf("name" to arguments?.getString("name"), "songId" to "aaa")
            findNavController().navigate(R.id.choose_to_song, bundle)
        }
    }
}
