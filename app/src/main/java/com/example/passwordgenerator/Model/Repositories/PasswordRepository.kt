package com.example.passwordgenerator.Model.Repositories

import androidx.lifecycle.LiveData
import com.example.passwordgenerator.Model.Entities.Password
import com.example.passwordgenerator.Model.Entities.PasswordDao

class PasswordRepository(val passwordDao: PasswordDao) {
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