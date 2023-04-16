package com.digalyst.myapplication.ui.map

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.digalyst.myapplication.base.ApiUserAdapter
import com.digalyst.myapplication.base.UserAdapter
import com.digalyst.myapplication.data.local.entity.User
import com.digalyst.myapplication.databinding.ActivitySingleNetworkCallBinding
import com.digalyst.myapplication.repo.Resource
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel

@AndroidEntryPoint
class MapActivity : AppCompatActivity() {
    lateinit var binding: ActivitySingleNetworkCallBinding
    private val viewModel: MapViewModel by viewModels()
    private lateinit var adapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySingleNetworkCallBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupUI()
        setupObserver()
    }

    private fun setupUI() {
        binding.apply {
            recyclerView.layoutManager = LinearLayoutManager(this@MapActivity)
            adapter =
                UserAdapter(
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

    private fun renderList(users: List<User>) {
        adapter.addData(users)
        adapter.notifyDataSetChanged()
    }
}