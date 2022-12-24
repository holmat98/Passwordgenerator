package com.mateuszholik.data.repositories

import com.mateuszholik.data.repositories.models.NewPassword
import com.mateuszholik.data.repositories.models.Password
import com.mateuszholik.data.repositories.models.UpdatedPassword
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Single

interface PasswordsRepository {

    fun createAndGetId(newPassword: NewPassword): Single<Long>

    fun delete(passwordId: Long): Completable

    fun update(updatedPassword: UpdatedPassword): Completable

    fun getPassword(passwordId: Long): Maybe<Password>

    fun getAllPasswords(): Single<List<Password>>
}