package com.example.tendable.test.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.tendable.test.db.getDatabase
import com.example.tendable.test.model.InspectionModel
import com.example.tendable.test.model.InspectionResponse
import com.example.tendable.test.model.LoginModel
import com.example.tendable.test.repository.LoginRepository
import com.example.tendable.test.repository.MainRepository
import kotlinx.coroutines.launch

class MainVM(application: Application) : AndroidViewModel(application) {

    private val mainRepository = MainRepository(getDatabase(application))

    private val _savedInspection = MutableLiveData<InspectionResponse>()
    val savedInspection : LiveData<InspectionResponse> get() = _savedInspection

    private val _startInspection = MutableLiveData<InspectionResponse>()
    val startInspection : LiveData<InspectionResponse> get() = _startInspection

    private val _submitInspection = MutableLiveData<Boolean>()
    val submitInspection : LiveData<Boolean> get() = _submitInspection

    private val _saveDraftInspection = MutableLiveData<Boolean>()
    val saveDraftInspection : LiveData<Boolean> get() = _saveDraftInspection

    fun callStartInspection() {
        viewModelScope.launch {
            val result = mainRepository.callStartInspection()
            _startInspection.value = result
        }
    }

    fun getSavedInspection() {
        viewModelScope.launch {
            val result = mainRepository.getDraftedInspection()
            _savedInspection.value = result
        }
    }

    fun saveDraftInspection(inspectionModel: InspectionModel) {
        viewModelScope.launch {
            val result = mainRepository.saveDraftInspection(inspectionModel)
            _saveDraftInspection.value = result
        }
    }

    fun callSubmitInspection(inspectionResponse : InspectionResponse) {
        viewModelScope.launch {
            val result = mainRepository.submitInspection(inspectionResponse)
            _submitInspection.value = result
        }
    }

    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MainVM::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return MainVM(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}