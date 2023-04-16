package com.digalyst.myapplication.ui.completion

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.digalyst.myapplication.base.ApiUserAdapter
import com.digalyst.myapplication.databinding.ActivityCompletionBinding
import com.digalyst.myapplication.repo.Resource
import com.mindorks.kotlinFlow.learn.completion.CompletionViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CompletionActivity : AppCompatActivity() {

    lateinit var binding: ActivityCompletionBinding
    private val viewModel: CompletionViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCompletionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupObserver()
    }

    private fun setupObserver() {
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
    }
}