package com.mateuszholik.data.repositories

import com.mateuszholik.data.repositories.models.Password
import com.mateuszholik.data.repositories.models.Resource
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

interface PasswordsRepository {

    fun insert(password: Password): Completable

    fun delete(password: Password): Completable

    fun update(password: Password): Completable

    fun getAllPasswords(): Single<Resource<List<Password>>>
}