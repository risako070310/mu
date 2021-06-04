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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.fragment_choose.*
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class EditChooseFragment : Fragment(), ResultViewHolder.ItemClickListener {

    private var token = ""
    private lateinit var resultData: Data

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_choose, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val resultView: RecyclerView = view.findViewById(R.id.resultView)
        resultView.layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL,false)

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
                        musicService.searchMusic("Bearer $token", "ja;q=1", text.toString(), "track", 10)
                    }.onSuccess {
                        resultView.adapter = ResultAdapter(requireContext(), this@EditChooseFragment, it)
                        resultData = it
                    }.onFailure {
                        Log.d("error", it.message.toString())
                    }
                }
            }
        }
    }

    override fun onItemClick(view: View, position: Int) {
        val bundle = bundleOf("name" to arguments?.getString("name"), "songId" to resultData.trackData.items[position].id)
        findNavController().navigate(R.id.edit_choose_to_song, bundle)
    }

}