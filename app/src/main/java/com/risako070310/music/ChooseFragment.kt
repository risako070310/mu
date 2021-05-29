package com.risako070310.music

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_choose.*

class ChooseFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_choose, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        nextButton.setOnClickListener {
//            val songName = songEditText.text.toString()
//            val songId = songEditText.text.toString().substring(31, 53)
//            text.text = songId
            val bundle = bundleOf("name" to arguments?.getString("name"), "songId" to "aaa")
            findNavController().navigate(R.id.choose_to_song, bundle)
        }
    }
}