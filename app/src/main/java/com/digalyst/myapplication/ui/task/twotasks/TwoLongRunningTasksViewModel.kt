package com.digalyst.myapplication.ui.task.twotasks

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.digalyst.myapplication.data.api.ApiHelper
import com.digalyst.myapplication.repo.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TwoLongRunningTasksViewModel @Inject constructor(
    private val apiHelper: ApiHelper,
) : ViewModel() {

    private val status = MutableLiveData<Resource<String>>()

    fun startLongRunningTask() {
        viewModelScope.launch {
            status.postValue(Resource.Loading)
            doLongRunningTaskOne()
                .zip(doLongRunningTaskTwo()) { resultOne, resultTwo ->
                    return@zip resultOne + resultTwo
                }
                .flowOn(Dispatchers.Default)
                .catch { e ->
                    status.postValue(Resource.Fail(e))
                }
                .collect {
                    status.postValue(Resource.Success(it))
                }
        }
    }

    fun getStatus(): LiveData<Resource<String>> {
        return status
    }

    private fun doLongRunningTaskTwo(): Flow<String> {
        return flow {
            // your code for doing a long running task
            // Added delay to simulate
            delay(5000)
            emit("Two")
        }
    }

    private fun doLongRunningTaskOne(): Flow<String> {
        return flow {
            // your code for doing a long running task
            // Added delay to simulate
            delay(5000)
            emit("One")
        }
    }

}