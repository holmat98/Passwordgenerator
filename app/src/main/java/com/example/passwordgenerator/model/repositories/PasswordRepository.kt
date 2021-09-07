package com.example.passwordgenerator.model.repositories

import androidx.lifecycle.LiveData
import com.example.passwordgenerator.model.entities.Password
import com.example.passwordgenerator.model.entities.PasswordDao

class PasswordRepository(private val passwordDao: PasswordDao) {
    suspend fun insert(password: Password){
        passwordDao.insert(password)
    }

    suspend fun delete(password: Password){
        passwordDao.delete(password)
    }

    suspend fun update(password: Password){
        passwordDao.update(password)
    }

    fun getAllPasswords(): LiveData<List<Password>>{
        return passwordDao.getAllPasswords()
    }
}