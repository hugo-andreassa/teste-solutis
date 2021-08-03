package com.example.android.testesolutis.ui.login

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.android.testesolutis.R
import com.example.android.testesolutis.databinding.ActivityLoginBinding
import com.example.android.testesolutis.db.getDatabase
import com.example.android.testesolutis.ui.main.MainActivity
import com.example.android.testesolutis.utils.isCPF
import com.example.android.testesolutis.utils.isEmail
import com.example.android.testesolutis.utils.isPassword
import com.google.android.material.snackbar.Snackbar

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val database = getDatabase(this)
        viewModel = ViewModelProvider(
            this,
            LoginViewModel.Factory(database)
        ).get(LoginViewModel::class.java)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        setupErrorMessage()
        setupNavigation()
        setupLogin()
    }

    private fun setupLogin() {
        binding.buttonLogin.setOnClickListener {
            val user = binding.textInputUser.text.toString()
            val password = binding.textInputPassword.text.toString()

            if (validateLogin(user, password)) {
                viewModel.login(user, password)
            }
        }
    }

    private fun setupNavigation() {
        viewModel.navigateToMainActivity.observe(this, {
            it?.let {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                viewModel.doneNavigation()
                finish()
            }
        })
    }

    private fun setupErrorMessage() {
        viewModel.errorMessage.observe(this, { errorMessage ->
            errorMessage?.let {
                Snackbar
                    .make(
                        findViewById(android.R.id.content),
                        it,
                        Snackbar.LENGTH_LONG
                    )
                    .setBackgroundTint(getColor(R.color.snackbarErrorColor))
                    .show()

                viewModel.doneShowingError()
            }
        })
    }

    private fun validateLogin(user: String, password: String): Boolean {

        if (isCPF(user) || isEmail(user)) {
            binding.textInputLayoutUser.error = null
        } else {
            binding.textInputLayoutUser.error = getString(R.string.user_field_error)
            return false
        }

        if (isPassword(password)) {
            binding.textInputLayoutPassword.error = null
        } else {
            binding.textInputLayoutPassword.error = getString(R.string.password_field_error)
            return false
        }

        return true
    }
}