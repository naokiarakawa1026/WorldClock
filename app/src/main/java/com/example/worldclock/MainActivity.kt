package com.example.worldclock

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
            startActivity(intent)
        }
    }
}