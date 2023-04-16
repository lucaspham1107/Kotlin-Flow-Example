package com.digalyst.myapplication.ui.task.twotasks

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.digalyst.myapplication.databinding.ActivityLongRunningTaskBinding
import com.digalyst.myapplication.repo.Resource
import com.digalyst.myapplication.ui.task.onetask.LongRunningTaskViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TwoLongRunningTasksActivity : AppCompatActivity() {

    lateinit var binding: ActivityLongRunningTaskBinding
    private val viewModel: LongRunningTaskViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLongRunningTaskBinding.inflate(layoutInflater)
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
        viewModel.startLongRunningTask()
    }
}
