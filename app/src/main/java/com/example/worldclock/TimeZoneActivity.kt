package com.example.worldclock

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.worldclock.databinding.ActivityMainBinding
import com.example.worldclock.databinding.ActivityTimeZoneBinding

class TimeZoneActivity : AppCompatActivity() {

    var _binding : ActivityTimeZoneBinding? = null
    val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityTimeZoneBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setResult(Activity.RESULT_CANCELED)
        val list = binding.timeZoneClockList
        val adapter = TimeZoneAdapter(this)
        list.adapter = adapter

        list.setOnItemClickListener { parent, view, position, id ->
            val timeZone = adapter.getItem(position)
            Log.d("TimeZoneActivity", "${timeZone}")
            val result = Intent()
            result.putExtra("timeZone", timeZone)
            setResult(Activity.RESULT_OK, result)
            finish()
        }

    }
}