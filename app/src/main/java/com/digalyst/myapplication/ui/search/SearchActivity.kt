package com.digalyst.myapplication.ui.search

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.digalyst.myapplication.databinding.ActivitySearchBinding
import com.digalyst.myapplication.utils.getQueryTextChangeStateFlow
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlin.coroutines.CoroutineContext

class SearchActivity : AppCompatActivity(), CoroutineScope {

    lateinit var binding: ActivitySearchBinding
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    private lateinit var job: Job

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        job = Job()
        setUpSearchStateFlow()
    }

    override fun onDestroy() {
        job.cancel()
        super.onDestroy()
    }

    private fun setUpSearchStateFlow() {
        binding.apply {
            launch {
                searchView.getQueryTextChangeStateFlow()
                    .debounce(300)
                    .filter { query ->
                        if (query.isEmpty()) {
                            textViewResult.text = ""
                            return@filter false
                        } else {
                            return@filter true
                        }
                    }
                    .distinctUntilChanged()
                    .flatMapLatest { query ->
                        dataFromNetwork(query)
                            .catch {
                                emitAll(flowOf(""))
                            }
                    }
                    .flowOn(Dispatchers.Default)
                    .collect { result ->
                        textViewResult.text = result
                    }
            }
        }

    }

    /**
     * Simulation of network data
     */
    private fun dataFromNetwork(query: String): Flow<String> {
        return flow {
            delay(2000)
            emit(query)
        }
    }

}