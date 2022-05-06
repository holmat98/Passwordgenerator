package com.mateuszholik.data.repositories

import com.mateuszholik.data.cryptography.EncryptionManager
import com.mateuszholik.data.db.daos.PasswordsDao
import com.mateuszholik.data.db.models.PasswordDB
import com.mateuszholik.data.mappers.PasswordListMapper
import com.mateuszholik.data.repositories.models.Password
import com.mateuszholik.data.repositories.models.Resource
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import java.time.LocalDateTime

internal class PasswordsRepositoryImpl(
    private val passwordsDao: PasswordsDao,
    private val passwordListMapper: PasswordListMapper,
    private val encryptionManager: EncryptionManager
) : BaseRepository(), PasswordsRepository {

    override fun insert(platform: String, password: String): Completable {
        val encryptedPlatformName = encryptionManager.encrypt(platform)
        val encryptedPassword = encryptionManager.encrypt(password)

        val newPassword = PasswordDB(
            id = 0,
            platformName = encryptedPlatformName.data,
            platformIV = encryptedPlatformName.iv,
            password = encryptedPassword.data,
            passwordIV = encryptedPassword.iv,
            expiringDate = LocalDateTime.now().plusDays(90)
        )

        return passwordsDao.insert(newPassword)
    }

    override fun delete(password: Password): Completable =
        TODO()

    override fun update(password: Password): Completable =
        TODO()

    override fun getAllPasswords(): Single<Resource<List<Password>>> =
        passwordsDao.getAllPasswords()
            .map { passwordDBList ->
                createResource(passwordDBList) { passwordDB ->
                    passwordListMapper.map(passwordDB)
                }
            }
}