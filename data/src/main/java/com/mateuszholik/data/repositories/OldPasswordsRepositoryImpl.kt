package com.mateuszholik.data.repositories

import com.mateuszholik.data.db.daos.OldPasswordsDao
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

internal class OldPasswordsRepositoryImpl(
    private val oldPasswordsDao: OldPasswordsDao,
    private val passwordDBListToPasswordListMapper: PasswordDBListToPasswordListMapper,
    private val passwordDBToPasswordMapper: PasswordDBToPasswordMapper,
    private val newPasswordToPasswordDBMapper: NewPasswordToPasswordDBMapper,
    private val newPasswordsListToPasswordDBListMapper: NewPasswordsListToPasswordDBListMapper,
    private val updatedPasswordToPasswordDBMapper: UpdatedPasswordToPasswordDBMapper,
) : OldPasswordsRepository {

    override fun insertAndGetId(newPassword: NewPassword): Single<Long> =
        Single.just(newPasswordToPasswordDBMapper.map(newPassword))
            .flatMap { oldPasswordsDao.insertAndGetId(it) }

    override fun insertPasswords(newPasswords: List<NewPassword>): Completable =
        Single.just(newPasswordsListToPasswordDBListMapper.map(newPasswords))
            .flatMapCompletable { oldPasswordsDao.insertPasswords(it) }

    override fun delete(passwordId: Long): Completable =
        oldPasswordsDao.deletePassword(passwordId)

    override fun update(updatedPassword: UpdatedPassword): Completable =
        Single.just(updatedPasswordToPasswordDBMapper.map(updatedPassword))
            .flatMapCompletable { oldPasswordsDao.update(it) }

    override fun getPassword(passwordId: Long): Maybe<Password> =
        oldPasswordsDao.getPassword(passwordId)
            .map { passwordDBToPasswordMapper.map(it) }

    override fun getAllPasswords(): Single<List<Password>> =
        oldPasswordsDao.getAllPasswords()
            .map { passwordDBListToPasswordListMapper.map(it) }

    override fun updatePlatformAndPasswordScoreFor(
        id: Long,
        platform: String,
        passwordScore: Int,
    ): Completable =
        Completable.complete()
}
