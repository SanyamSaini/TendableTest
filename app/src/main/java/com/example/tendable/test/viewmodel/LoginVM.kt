package com.example.tendable.test.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.tendable.test.db.getDatabase
import com.example.tendable.test.model.LoginModel
import com.example.tendable.test.repository.LoginRepository
import kotlinx.coroutines.launch

class LoginVM(application: Application) : AndroidViewModel(application) {

    private val loginRepository = LoginRepository()

    private val _loginUser = MutableLiveData<String>()
    val loginUser : LiveData<String> get() = _loginUser

    private val _registerUser = MutableLiveData<String>()
    val registerUser : LiveData<String> get() = _registerUser

    fun loginUser(loginModel: LoginModel) {
        viewModelScope.launch {
            val result = loginRepository.loginUser(loginModel)
            _loginUser.value = result
        }
    }

    fun registerUser(loginModel: LoginModel) {
        viewModelScope.launch {
            val result = loginRepository.registerUser(loginModel)
            _registerUser.value = result
        }
    }

    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(LoginVM::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return LoginVM(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}