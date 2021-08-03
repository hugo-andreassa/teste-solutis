package com.example.android.testesolutis.ui.main

import androidx.lifecycle.*
import com.example.android.testesolutis.api.SolutisApi
import com.example.android.testesolutis.db.SolutisDatabase
import com.example.android.testesolutis.model.Extrato
import com.example.android.testesolutis.model.User
import kotlinx.coroutines.launch

const val TAG = "LoginViewModel"

class MainViewModel(private val database: SolutisDatabase) : ViewModel() {

    private val _loading = MutableLiveData<Boolean>()
    private val _errorMessage = MutableLiveData<String?>()
    private val _user = MutableLiveData<User>()
    private val _extrato = MutableLiveData<List<Extrato>>()
    private val _navigateToLoginActivity = MutableLiveData<Boolean?>()

    val loading: LiveData<Boolean>
        get() = _loading
    val errorMessage: LiveData<String?>
        get() = _errorMessage
    val user: LiveData<User>
        get() = _user
    val extrato: LiveData<List<Extrato>>
        get() = _extrato
    val navigateToLoginActivity: LiveData<Boolean?>
        get() = _navigateToLoginActivity

    init {
        _loading.value = true
        viewModelScope.launch {
            val user = loadUser()
            loadExtrato(user)
            _loading.value = false
        }
    }

    fun logout() {
        _loading.value = true
        viewModelScope.launch {
            database.userDao.deleteAll()
            _navigateToLoginActivity.value = true

            _loading.value = false
        }
    }

    fun doneShowingError() {
        _errorMessage.value = null
    }

    fun doneNavigationToLoginActivity() {
        _navigateToLoginActivity.value = null
    }

    private suspend fun loadUser(): User? {
        try {
            val user = database.userDao.getLoggedUser()
            _user.value = user
            return user
        } catch (e: Exception) {
            _errorMessage.value = e.message
        }

        return null
    }

    private suspend fun loadExtrato(user: User?) {
        try {
            user?.let {
                val response = SolutisApi.service.extrato(user.token)
                if (response.isSuccessful) {
                    _extrato.value = response.body()!!
                } else {
                    _errorMessage.value = "Error loading extrato"
                }
            }
        } catch (e: Exception) {
            _errorMessage.value = e.message
        }
    }

    class Factory(private val database: SolutisDatabase) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return MainViewModel(database) as T
            }

            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}