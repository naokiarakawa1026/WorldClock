package com.example.worldclock

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import com.example.worldclock.databinding.ActivityMainBinding
import java.util.*

class MainActivity : AppCompatActivity() {

    var _binding : ActivityMainBinding? = null
    val binding get() = _binding!!


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
            startActivityForResult(intent, 1)
//            startForResult.launch(intent)

        }
        showClock()
    }

    private fun showClock(){
        val pref = getSharedPreferences("prefs", Context.MODE_PRIVATE)
        Log.d("MainActivity", "${pref}")
        val timeZones = pref.getStringSet("time_zone", setOf())
        Log.d("MainActivity", "${timeZones}")
        val list = binding.clockList
        list.adapter = TimeZoneAdapter(this, timeZones!!.toTypedArray())
    }

    @SuppressLint("MissingSuperCall")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 1 && resultCode == Activity.RESULT_OK && data != null) {
            val timeZone = data.getStringExtra("timeZone")

            val pref = getSharedPreferences("prefs", Context.MODE_PRIVATE)
            val timeZones = pref.getStringSet("time_zone", mutableSetOf())?.toMutableSet()
                ?: mutableSetOf()

            timeZones.add(timeZone)
            pref.edit().putStringSet("time_zone", timeZones.toSet()).apply()
            showClock()
        }
    }
}