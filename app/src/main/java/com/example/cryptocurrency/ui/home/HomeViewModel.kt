package com.example.cryptocurrency.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cryptocurrency.data.models.CryptoResponse
import com.example.cryptocurrency.data.service.RemoteRepository
import com.example.cryptocurrency.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.jvm.internal.Intrinsics

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: RemoteRepository) : ViewModel() {

    private val _data : MutableLiveData<Resource<CryptoResponse>> = MutableLiveData()
    val data : LiveData<Resource<CryptoResponse>> = _data

    fun getData(){
        _data.postValue(Resource.Loading())
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                repository.getData()
            }.onSuccess {
                _data.postValue(Resource.Success(data = it))
            }.onFailure {
                _data.postValue(Resource.Error(message = it.message))
            }
        }
    }
}