package com.mateuszholik.data.repositories

import com.mateuszholik.data.repositories.models.NewPassword
import com.mateuszholik.data.repositories.models.Password
import com.mateuszholik.data.repositories.models.UpdatedPassword
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Single

interface OldPasswordsRepository {

    fun insertAndGetId(newPassword: NewPassword): Single<Long>

    fun insertPasswords(newPasswords: List<NewPassword>): Completable

    fun delete(passwordId: Long): Completable

    fun update(updatedPassword: UpdatedPassword): Completable

    fun getPassword(passwordId: Long): Maybe<Password>

    fun getAllPasswords(): Single<List<Password>>

    /**
     * This method is supposed to be used only for updating the database to the correct state
     * after migrating from version 2 to 3
     */
    fun updatePlatformAndPasswordScoreFor(
        id: Long,
        platform: String,
        passwordScore: Int,
    ): Completable
}
