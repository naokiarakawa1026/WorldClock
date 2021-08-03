package com.example.worldclock

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.worldclock.databinding.ActivityMainBinding
import java.util.*

class MainActivity : AppCompatActivity() {

    var _binding : ActivityMainBinding? = null
    val binding get() = _binding!!

    // startActivityForResultがdeprecatedになっていたため修正
    val startForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if ( result.resultCode == Activity.RESULT_OK && result.data != null) {
                Toast.makeText(this, "startForResult", Toast.LENGTH_SHORT).show()
                val timeZone = result.data!!.getStringExtra("timeZone")
                val pref = getSharedPreferences("prefs", Context.MODE_PRIVATE)
                val timeZones = pref.getStringSet("time_zone", mutableSetOf())?.toMutableSet()
                    ?: mutableSetOf()

                timeZones.add(timeZone)
                pref.edit().putStringSet("time_zone", timeZones.toSet()).apply()
                showClock()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val timezone = TimeZone.getDefault()
        val timeZoneView = binding.timeZone
        timeZoneView.text = timezone.displayName

        val addButton = binding.add
        addButton.setOnClickListener {
            val intent = Intent(this, TimeZoneActivity::class.java)
            startForResult.launch(intent)
        }
        showClock()
    }

    private fun showClock(){
        val pref = getSharedPreferences("prefs", Context.MODE_PRIVATE)
        val timeZones = pref.getStringSet("time_zone", setOf())
        val list = binding.clockList
        list.adapter = TimeZoneAdapter(this, timeZones!!.toTypedArray())
    }
}