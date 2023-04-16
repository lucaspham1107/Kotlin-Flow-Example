package com.digalyst.myapplication.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.digalyst.myapplication.databinding.ActivityMainBinding
import com.digalyst.myapplication.ui.completion.CompletionActivity
import com.digalyst.myapplication.ui.errorhandling.emitall.EmitAllActivity
import com.digalyst.myapplication.ui.filter.FilterActivity
import com.digalyst.myapplication.ui.map.MapActivity
import com.digalyst.myapplication.ui.parallel.ParallelNetworkCallActivity
import com.digalyst.myapplication.ui.retry.RetryActivity
import com.digalyst.myapplication.ui.retrywhen.RetryWhenActivity
import com.digalyst.myapplication.ui.series.SeriesNetworkCallActivity
import com.digalyst.myapplication.ui.singlenetworkcall.SingleNetworkCallActivity
import com.digalyst.myapplication.ui.task.onetask.LongRunningTaskActivity
import com.digalyst.myapplication.ui.errorhandling.catch.CatchActivity
import com.digalyst.myapplication.ui.search.SearchActivity
import com.digalyst.myapplication.ui.task.twotasks.TwoLongRunningTasksActivity
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
        binding.srNetworkCall.setOnClickListener {
            startActivity(Intent(this@MainActivity, SeriesNetworkCallActivity::class.java))
        }
        binding.paraNetworkCall.setOnClickListener {
            startActivity(Intent(this@MainActivity, ParallelNetworkCallActivity::class.java))
        }

        binding.map.setOnClickListener {
            startActivity(Intent(this@MainActivity, MapActivity::class.java))
        }
        binding.retry.setOnClickListener {
            startActivity(Intent(this@MainActivity, RetryActivity::class.java))
        }
        binding.retryWhen.setOnClickListener {
            startActivity(Intent(this@MainActivity, RetryWhenActivity::class.java))

        }
        binding.catchError.setOnClickListener {
            startActivity(Intent(this@MainActivity, CatchActivity::class.java))

        }
        binding.emitAllError.setOnClickListener {
            startActivity(Intent(this@MainActivity, EmitAllActivity::class.java))
        }
        binding.compeletion.setOnClickListener {
            startActivity(Intent(this@MainActivity, CompletionActivity::class.java))

        }
        binding.longRun.setOnClickListener {
            startActivity(Intent(this@MainActivity, LongRunningTaskActivity::class.java))
        }
        binding.twoLongRun.setOnClickListener {
            startActivity(Intent(this@MainActivity, TwoLongRunningTasksActivity::class.java))
        }
        binding.filter.setOnClickListener {
            startActivity(Intent(this@MainActivity, FilterActivity::class.java))
        }
        binding.search.setOnClickListener {
            startActivity(Intent(this@MainActivity, SearchActivity::class.java))
        }
    }

}