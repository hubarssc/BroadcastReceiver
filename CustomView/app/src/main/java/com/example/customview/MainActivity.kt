package com.example.customview

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.customview.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var customProgressBar: CustomProgressBar
    private lateinit var percentEditText: EditText
    private lateinit var applyButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        customProgressBar = binding.customProgressBar
        percentEditText = binding.percentEditText
        applyButton = binding.applyButton

        applyButton.setOnClickListener {
            val inputPercent = percentEditText.text.toString().toIntOrNull()
            if (inputPercent != null && inputPercent in 0..100) {
                customProgressBar.setProgress(inputPercent)
            }
        }
    }
}