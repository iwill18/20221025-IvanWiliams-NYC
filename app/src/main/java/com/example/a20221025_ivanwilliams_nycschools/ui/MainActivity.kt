package com.example.a20221025_ivanwilliams_nycschools.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.a20221025_ivanwilliams_nycschools.R
import com.example.a20221025_ivanwilliams_nycschools.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        showDisplayFragment()
    }

    private fun showDisplayFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, SchoolDisplayFragment())
            .commit()
    }
}