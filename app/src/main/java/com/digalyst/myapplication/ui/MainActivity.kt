package com.digalyst.myapplication.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.digalyst.myapplication.R
import com.digalyst.myapplication.databinding.ActivityMainBinding
import com.digalyst.myapplication.ui.singlenetworkcall.SingleNetworkCallActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.sgNetworkCall.setOnClickListener {
            startActivity(Intent(this@MainActivity, SingleNetworkCallActivity::class.java))
        }
    }

}