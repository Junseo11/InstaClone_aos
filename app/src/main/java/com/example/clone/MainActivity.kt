package com.example.clone

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.clone.databinding.ActivityLoginBinding
import com.example.clone.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}