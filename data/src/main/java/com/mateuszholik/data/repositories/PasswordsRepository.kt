package com.mateuszholik.data.repositories

import com.mateuszholik.data.repositories.models.Password
import com.mateuszholik.data.repositories.models.Resource
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

interface PasswordsRepository {

    fun insert(platform: String, password: String): Completable

    fun delete(passwordId: Long): Completable

    fun update(password: Password): Completable

    fun getAllPasswords(): Single<List<Password>>
}