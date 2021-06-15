package com.risako070310.music.login

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.risako070310.music.R
import com.risako070310.music.main.MainActivity
import kotlinx.android.synthetic.main.fragment_name.*

class NameFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_name, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        nextButton.setOnClickListener {
            if (nameEditText.text!!.isEmpty()){
                nameEditText.error = "名前の入力は必須です"
            } else {
                val name = nameEditText.text.toString()
                val bundle = bundleOf("name" to name)
                findNavController().navigate(R.id.name_to_choose, bundle)
            }
        }
    }
}
