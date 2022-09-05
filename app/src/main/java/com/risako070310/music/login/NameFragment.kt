package com.risako070310.music.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.risako070310.music.R
import com.risako070310.music.databinding.FragmentNameBinding

class NameFragment : Fragment() {

    lateinit var binding: FragmentNameBinding

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

        binding.nextButton.setOnClickListener {
            val name = binding.nameEditText.text.toString()
            val bundle = bundleOf("name" to name)
            findNavController().navigate(R.id.name_to_choose, bundle)
        }
    }
}
