package com.example.android.testesolutis.ui.login

import android.util.Log
import androidx.lifecycle.*
import com.example.android.testesolutis.api.SolutisApi
import com.example.android.testesolutis.db.SolutisDatabase
import com.example.android.testesolutis.model.Login
import com.example.android.testesolutis.model.User
import kotlinx.coroutines.launch

const val TAG = "LoginViewModel"

class LoginViewModel(private val database: SolutisDatabase) : ViewModel() {

    private val _loading = MutableLiveData<Boolean>()
    private val _errorMessage = MutableLiveData<String?>()
    private val _savedLoginData = MutableLiveData<Login>()
    private val _navigateToMainActivity = MutableLiveData<Boolean>()

    val loading: LiveData<Boolean>
        get() = _loading
    val errorMessage: LiveData<String?>
        get() = _errorMessage
    val savedLoginData: LiveData<Login>
        get() = _savedLoginData
    val navigateToMainActivity: LiveData<Boolean?>
        get() = _navigateToMainActivity

    init {
        _loading.value = true
        viewModelScope.launch {
            checkLoginData()
            _loading.value = false
        }
    }

    fun login(user: String, password: String) {
        _loading.value = true
        viewModelScope.launch {
            try {
                val login = Login(user, password)
                val response = SolutisApi.service.login(login)
                if (response.isSuccessful) {
                    saveUserData(response.body()!!)
                    saveLoginData(login)

                    _navigateToMainActivity.value = true
                } else {
                    _errorMessage.value = "Incorrect user data"
                }
            } catch (e: Exception) {
                Log.e(TAG, e.message.toString())
                _errorMessage.value =
                    "An error occurred while trying to communicate with the server"
            }

            _loading.value = false
        }
    }

    fun doneShowingError() {
        _errorMessage.value = null
    }

    fun doneNavigation() {
        _navigateToMainActivity.value = null
    }

    private suspend fun saveUserData(user: User) {
        // Clean user table and insert user
        database.userDao.deleteAll()
        database.userDao.insert(user)
    }

    private suspend fun saveLoginData(login: Login) {
        // Clean login table and insert login
        database.loginDao.deleteAll()
        database.loginDao.insert(login)
    }

    private suspend fun checkLoginData() {
        try {
            val login = database.loginDao.getLogin()
            login?.let {
                _savedLoginData.value = it
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error checking user: ${e.message}")
        }
    }

    class Factory(private val database: SolutisDatabase) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return LoginViewModel(database) as T
            }

            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}