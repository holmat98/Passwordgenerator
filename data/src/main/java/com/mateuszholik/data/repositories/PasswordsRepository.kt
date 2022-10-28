package com.mateuszholik.data.repositories

import com.mateuszholik.data.repositories.models.Password
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Single

interface PasswordsRepository {

    fun insert(platform: String, password: String): Single<Long>

    fun delete(passwordId: Long): Completable

    fun update(password: Password): Completable

    fun getPassword(passwordId: Long): Maybe<Password>

    fun getAllPasswords(): Single<List<Password>>
}