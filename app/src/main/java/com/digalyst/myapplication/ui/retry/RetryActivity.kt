package com.digalyst.myapplication.ui.retry

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.digalyst.myapplication.databinding.ActivityRetryBinding
import com.digalyst.myapplication.repo.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RetryActivity : AppCompatActivity() {

    lateinit var binding: ActivityRetryBinding
    private val viewModel: RetryViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRetryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupLongRunningTask()
    }

    private fun setupLongRunningTask() {
        viewModel.getStatus().observe(this, Observer {
            when (it) {
                is Resource.Success -> {
                    binding.progressBar.visibility = View.GONE
                    binding.textView.text = it.valueOrNull
                    binding.textView.visibility = View.VISIBLE
                }
                is Resource.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.textView.visibility = View.GONE
                }
                is Resource.Fail -> {
                    //Handle Error
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(this, it.error.message, Toast.LENGTH_SHORT).show()
                }
            }
        })
        viewModel.startTask()
    }

}
