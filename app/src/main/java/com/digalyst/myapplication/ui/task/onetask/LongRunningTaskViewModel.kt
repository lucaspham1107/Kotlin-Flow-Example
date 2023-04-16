package com.digalyst.myapplication.ui.task.onetask

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.digalyst.myapplication.data.api.ApiHelper
import com.digalyst.myapplication.repo.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LongRunningTaskViewModel @Inject constructor(
    private val apiHelper: ApiHelper,
) : ViewModel() {

    private val status = MutableLiveData<Resource<String>>()

    fun startLongRunningTask() {
        viewModelScope.launch {
            status.postValue(Resource.Loading)
            // do a long running task
            doLongRunningTask()
                .flowOn(Dispatchers.Default)
                .catch {
                    status.postValue(Resource.Fail(Throwable("Something Went Wrong")))
                }
                .collect {
                    status.postValue(Resource.Success("Task Completed"))
                }
        }
    }

    fun getStatus(): LiveData<Resource<String>> {
        return status
    }

    private fun doLongRunningTask(): Flow<Int> {
        return flow {
            // your code for doing a long running task
            // Added delay to simulate
            delay(5000)
            emit(0)
        }
    }
}