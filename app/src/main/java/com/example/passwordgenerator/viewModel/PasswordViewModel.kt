package com.example.passwordgenerator.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.passwordgenerator.model.entities.Password
import com.example.passwordgenerator.model.PasswordDatabase
import com.example.passwordgenerator.model.repositories.PasswordRepository
import kotlinx.coroutines.launch

class PasswordViewModel(application: Application): AndroidViewModel(application) {
    val passwordRepository: PasswordRepository
    var passwords: LiveData<List<Password>>

    init {
        passwordRepository = PasswordRepository(PasswordDatabase.getDatabase(application).passwordDao())
        passwords = PasswordDatabase.getDatabase(application).passwordDao().getAllPasswords()
    }

    fun insert(platformName: String, password: String){
        viewModelScope.launch {
            passwordRepository.insert(Password(id = 0, platformName = platformName, password = password))
        }
    }

    fun update(password: Password){
        viewModelScope.launch {
            passwordRepository.update(password)
        }
    }

    fun delete(password: Password){
        viewModelScope.launch {
            passwordRepository.delete(password)
        }
    }
}