package com.risako070310.music.main

import android.Manifest.*
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.media.audiofx.BassBoost
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.common.reflect.Reflection.getPackageName
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.risako070310.music.R
import kotlinx.android.synthetic.main.fragment_location.*

class LocationFragment : Fragment() {

    private val db = Firebase.firestore
    private lateinit var userId:String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_location, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val userData: SharedPreferences = requireContext().getSharedPreferences(
            "userId",
            AppCompatActivity.MODE_PRIVATE
        )
        userId = userData.getString("user", "hogehoge")!!

        db.collection("users").document(userId)
            .get()
            .addOnCompleteListener {
                val user = it.result
                if (user != null && user.data != null) {
                    if (user.data?.get("locationSwitch") == "true") {
                        locationSwitch.isChecked = true
                        checkPermission()
                    }
                }
            }

        locationSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                checkPermission()
            }
            val updates = hashMapOf<String, Any>("locationSwitch" to isChecked.toString())
            db.collection("users").document(userId).update(updates)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == 100) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                locationSwitch.isChecked = true
                val updates = hashMapOf<String, Any>("locationSwitch" to "true")
                db.collection("users").document(userId).update(updates)

                val intent = Intent(requireActivity(), InfoActivity::class.java)
                startActivity(intent)
            } else {
                locationSwitch.isChecked = false
                val updates = hashMapOf<String, Any>("locationSwitch" to "false")
                db.collection("users").document(userId).update(updates)
            }
        }
    }

    private fun checkPermission(){
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_DENIED
        ) {
            locationSwitch.isChecked = false
            val updates = hashMapOf<String, Any>("locationSwitch" to "false")
            db.collection("users").document(userId).update(updates)

            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(permission.ACCESS_FINE_LOCATION),
                100
            )
            AlertDialog.Builder(requireContext())
                .setTitle("使用の許可")
                .setMessage("「設定」アプリから位置情報の利用権限の許可をしてください")
                .setPositiveButton("OK") { _, _ ->
                    val intent =
                        Intent(
                            android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                            Uri.parse("package:" + activity?.packageName)
                        )
                    startActivity(intent)
                }
                .setCancelable(true)
                .show()
        }
    }
}

