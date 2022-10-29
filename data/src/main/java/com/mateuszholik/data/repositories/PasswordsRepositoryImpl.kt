package com.mateuszholik.data.repositories

import com.mateuszholik.data.db.daos.PasswordsDao
import com.mateuszholik.data.managers.io.SharedPrefKeys.PASSWORD_VALIDITY
import com.mateuszholik.data.managers.io.SharedPrefManager
import com.mateuszholik.data.mappers.PasswordDBMapper
import com.mateuszholik.data.mappers.PasswordListMapper
import com.mateuszholik.data.mappers.PasswordMapper
import com.mateuszholik.data.repositories.models.Password
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Single
import java.time.LocalDateTime

internal class PasswordsRepositoryImpl(
    private val passwordsDao: PasswordsDao,
    private val passwordListMapper: PasswordListMapper,
    private val passwordMapper: PasswordMapper,
    private val passwordDBMapper: PasswordDBMapper,
    private val sharedPrefManager: SharedPrefManager
) : PasswordsRepository {

    override fun insert(platform: String, password: String): Single<Long> =
        Single.just(sharedPrefManager.readLong(PASSWORD_VALIDITY))
            .map {
                Password(
                    id = DEFAULT_ID,
                    platformName = platform,
                    password = password,
                    expiringDate = LocalDateTime.now().plusDays(it)
                )
            }
            .map { passwordDBMapper.map(it) }
            .flatMap { passwordsDao.insert(it) }

    override fun delete(passwordId: Long): Completable =
        passwordsDao.deletePassword(passwordId)

    override fun update(password: Password): Completable =
        Single.just(sharedPrefManager.readLong(PASSWORD_VALIDITY))
            .map {
                passwordDBMapper.map(
                    password.copy(
                        expiringDate = LocalDateTime.now().plusDays(it)
                    )
                )
            }
            .flatMapCompletable { passwordsDao.update(it) }

    override fun getPassword(passwordId: Long): Maybe<Password> =
        passwordsDao.getPassword(passwordId)
            .map { passwordMapper.map(it) }

    override fun getAllPasswords(): Single<List<Password>> =
        passwordsDao.getAllPasswords()
            .map { passwordListMapper.map(it) }

    private companion object {
        const val DEFAULT_ID = 0L
    }
}