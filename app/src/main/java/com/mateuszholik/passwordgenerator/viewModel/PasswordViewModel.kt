package com.mateuszholik.passwordgenerator.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.mateuszholik.passwordgenerator.model.entities.Password
import com.mateuszholik.passwordgenerator.model.PasswordDatabase
import com.mateuszholik.passwordgenerator.model.repositories.PasswordRepository
import kotlinx.coroutines.launch

class PasswordViewModel(application: Application): AndroidViewModel(application) {
    val passwordRepository: PasswordRepository
    var passwords: LiveData<List<Password>>

    init {
        passwordRepository = PasswordRepository(PasswordDatabase.getDatabase(application).passwordDao())
        passwords = PasswordDatabase.getDatabase(application).passwordDao().getAllPasswords()
    }

    fun insert(platformName: String, password: String, passwordIv: String){
        viewModelScope.launch {
            passwordRepository.insert(Password(id = 0, platformName = platformName, password = password, passwordIv = passwordIv))
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