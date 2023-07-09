package com.mateuszholik.data.repositories

import com.mateuszholik.data.db.daos.PasswordsDao
import com.mateuszholik.data.mappers.NewPasswordToPasswordDBMapper
import com.mateuszholik.data.mappers.NewPasswordsListToPasswordDBListMapper
import com.mateuszholik.data.mappers.PasswordDBListToPasswordListMapper
import com.mateuszholik.data.mappers.PasswordDBToPasswordMapper
import com.mateuszholik.data.mappers.UpdatedPasswordToPasswordDBMapper
import com.mateuszholik.data.repositories.models.NewPassword
import com.mateuszholik.data.repositories.models.Password
import com.mateuszholik.data.repositories.models.UpdatedPassword
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Single

internal class PasswordsRepositoryImpl(
    private val passwordsDao: PasswordsDao,
    private val passwordDBListToPasswordListMapper: PasswordDBListToPasswordListMapper,
    private val passwordDBToPasswordMapper: PasswordDBToPasswordMapper,
    private val newPasswordToPasswordDBMapper: NewPasswordToPasswordDBMapper,
    private val newPasswordsListToPasswordDBListMapper: NewPasswordsListToPasswordDBListMapper,
    private val updatedPasswordToPasswordDBMapper: UpdatedPasswordToPasswordDBMapper,
) : PasswordsRepository {

    override fun insertAndGetId(newPassword: NewPassword): Single<Long> =
        Single.just(newPasswordToPasswordDBMapper.map(newPassword))
            .flatMap { passwordsDao.insertAndGetId(it) }

    override fun insertPasswords(newPasswords: List<NewPassword>): Completable =
        Single.just(newPasswordsListToPasswordDBListMapper.map(newPasswords))
            .flatMapCompletable { passwordsDao.insertPasswords(it) }

    override fun delete(passwordId: Long): Completable =
        passwordsDao.deletePassword(passwordId)

    override fun update(updatedPassword: UpdatedPassword): Completable =
        Single.just(updatedPasswordToPasswordDBMapper.map(updatedPassword))
            .flatMapCompletable { passwordsDao.update(it) }

    override fun getPassword(passwordId: Long): Maybe<Password> =
        passwordsDao.getPassword(passwordId)
            .map { passwordDBToPasswordMapper.map(it) }

    override fun getAllPasswords(): Single<List<Password>> =
        passwordsDao.getAllPasswords()
            .map { passwordDBListToPasswordListMapper.map(it) }

    override fun updatePlatformAndPasswordScoreFor(
        id: Long,
        platform: String,
        passwordScore: Int,
    ): Completable =
        Completable.complete()
}
