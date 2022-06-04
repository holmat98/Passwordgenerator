package com.mateuszholik.data.repositories

import com.mateuszholik.data.cryptography.EncryptionManager
import com.mateuszholik.data.db.daos.PasswordsDao
import com.mateuszholik.data.db.models.PasswordDB
import com.mateuszholik.data.mappers.PasswordListMapper
import com.mateuszholik.data.repositories.models.Password
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import java.time.LocalDateTime

internal class PasswordsRepositoryImpl(
    private val passwordsDao: PasswordsDao,
    private val passwordListMapper: PasswordListMapper,
    private val encryptionManager: EncryptionManager
) : PasswordsRepository {

    override fun insert(platform: String, password: String): Completable =
        Single.zip(
            Single.just(encryptionManager.encrypt(platform)),
            Single.just(encryptionManager.encrypt(password))
        ) { encryptedPlatformName, encryptedPassword ->
            PasswordDB(
                id = DEFAULT_ID,
                platformName = encryptedPlatformName.data,
                platformIV = encryptedPlatformName.iv,
                password = encryptedPassword.data,
                passwordIV = encryptedPassword.iv,
                expiringDate = LocalDateTime.now().plusDays(ADDITIONAL_DAYS_TO_EXPIRING_DATE)
            )
        }.flatMapCompletable { passwordsDao.insert(it) }

    override fun delete(passwordId: Long): Completable =
        passwordsDao.deletePassword(passwordId)

    override fun update(password: Password): Completable =
        Single.zip(
            Single.just(encryptionManager.encrypt(password.platformName)),
            Single.just(encryptionManager.encrypt(password.password))
        ) { encryptedPlatformName, encryptedPassword ->
            PasswordDB(
                id = password.id,
                platformName = encryptedPlatformName.data,
                platformIV = encryptedPlatformName.iv,
                password = encryptedPassword.data,
                passwordIV = encryptedPassword.iv,
                expiringDate = LocalDateTime.now().plusDays(ADDITIONAL_DAYS_TO_EXPIRING_DATE)
            )
        }.flatMapCompletable { passwordsDao.update(it) }

    override fun getAllPasswords(): Single<List<Password>> =
        passwordsDao.getAllPasswords()
            .map { passwordListMapper.map(it) }


    private companion object {
        const val ADDITIONAL_DAYS_TO_EXPIRING_DATE = 90L
        const val DEFAULT_ID = 0L
    }
}