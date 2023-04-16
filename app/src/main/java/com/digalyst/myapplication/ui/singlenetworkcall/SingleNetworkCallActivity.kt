package com.digalyst.myapplication.ui.singlenetworkcall

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.digalyst.myapplication.base.ApiUserAdapter
import com.digalyst.myapplication.data.model.ApiUser
import com.digalyst.myapplication.databinding.ActivitySingleNetworkCallBinding
import com.digalyst.myapplication.repo.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SingleNetworkCallActivity : AppCompatActivity() {
    lateinit var binding: ActivitySingleNetworkCallBinding
    private val viewModel: SingleNetworkCallViewModel by viewModels()
    private lateinit var adapter: ApiUserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySingleNetworkCallBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupUI()
        setupObserver()
    }

    private fun setupUI() {
        binding.apply {
            recyclerView.layoutManager = LinearLayoutManager(this@SingleNetworkCallActivity)
            adapter =
                ApiUserAdapter(
                    arrayListOf()
                )
            recyclerView.addItemDecoration(
                DividerItemDecoration(
                    recyclerView.context,
                    (recyclerView.layoutManager as LinearLayoutManager).orientation
                )
            )
            recyclerView.adapter = adapter
        }
    }

    private fun setupObserver() {
        viewModel.getUsers().observe(this) {
            when (it) {
                is Resource.Success -> {
                    binding.progressBar.visibility = View.GONE
                    it.valueOrNull?.let { users -> renderList(users) }
                    binding.recyclerView.visibility = View.VISIBLE
                }
                is Resource.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.recyclerView.visibility = View.GONE
                }
                is Resource.Fail -> {
                    //Handle Error
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(this, it.error.message, Toast.LENGTH_SHORT).show()
                }
                else -> Unit
            }
        }
    }

    private fun renderList(users: List<ApiUser>) {
        adapter.addData(users)
        adapter.notifyDataSetChanged()
    }


}