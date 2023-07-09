package com.mateuszholik.data.repositories

import com.mateuszholik.cryptography.KeyBaseEncryptionManager
import com.mateuszholik.data.db.daos.NamesDao
import com.mateuszholik.data.db.daos.OldPasswordsDao
import com.mateuszholik.data.db.daos.PasswordsDao
import com.mateuszholik.data.db.models.entities.NamesEntity
import com.mateuszholik.data.db.models.entities.OldPasswordEntity
import com.mateuszholik.data.db.models.entities.PasswordEntity
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

class MigrationRepositoryTest {

    private val oldPasswordsDao = mockk<OldPasswordsDao> {
        every { getAllPasswords() } returns Single.just(
            listOf(
                OLD_PASSWORD_ENTITY,
                OLD_PASSWORD_ENTITY_2,
                OLD_PASSWORD_ENTITY_3
            )
        )
        every { deletePassword(any()) } returns Completable.complete()
    }
    private val namesDao = mockk<NamesDao> {
        every { insertAndGetId(NAMES_ENTITY) } returns Single.just(1)
        every { insertAndGetId(NAMES_ENTITY_2) } returns Single.just(2)
        every { insertAndGetId(NAMES_ENTITY_3) } returns Single.just(3)
    }
    private val passwordsDao = mockk<PasswordsDao> {
        every { insert(PASSWORD_ENTITY) } returns Completable.complete()
        every { insert(PASSWORD_ENTITY_2) } returns Completable.complete()
        every { insert(PASSWORD_ENTITY_3) } returns Completable.complete()
    }
    private val encryptionManager = mockk<KeyBaseEncryptionManager> {
        every { decrypt(any()) } returns DECRYPTED_PASSWORD
    }

    private val migrationRepository = MigrationRepositoryImpl(
        oldPasswordsDao = oldPasswordsDao,
        namesDao = namesDao,
        passwordsDao = passwordsDao,
        encryptionManager = encryptionManager
    )

    @Test
    fun `Passwords from oldPasswordEntity are moved to NamesEntity and to NewPasswordsEntity correctly`() {

        migrationRepository.migrate { Single.just(PASSWORD_SCORE) }
            .test()
            .assertComplete()

        verify(exactly = 1) { namesDao.insertAndGetId(NAMES_ENTITY) }
        verify(exactly = 1) { namesDao.insertAndGetId(NAMES_ENTITY_2) }
        verify(exactly = 1) { namesDao.insertAndGetId(NAMES_ENTITY_3) }
        verify(exactly = 1) { passwordsDao.insert(PASSWORD_ENTITY) }
        verify(exactly = 1) { passwordsDao.insert(PASSWORD_ENTITY_2) }
        verify(exactly = 1) { passwordsDao.insert(PASSWORD_ENTITY_3) }
        verify(exactly = 1) { oldPasswordsDao.deletePassword(1) }
        verify(exactly = 1) { oldPasswordsDao.deletePassword(2) }
        verify(exactly = 1) { oldPasswordsDao.deletePassword(3) }
    }

    @Test
    fun `When migration of one password didn't gone correctly the rest was migrated`() {
        every {
            namesDao.insertAndGetId(NAMES_ENTITY_2)
        } returns Single.error(Exception("Insertion finished with error"))

        migrationRepository.migrate { Single.just(PASSWORD_SCORE) }
            .test()
            .assertComplete()

        verify(exactly = 1) { namesDao.insertAndGetId(NAMES_ENTITY) }
        verify(exactly = 1) { namesDao.insertAndGetId(NAMES_ENTITY_2) }
        verify(exactly = 1) { namesDao.insertAndGetId(NAMES_ENTITY_3) }
        verify(exactly = 1) { passwordsDao.insert(PASSWORD_ENTITY) }
        verify(exactly = 0) { passwordsDao.insert(PASSWORD_ENTITY_2) }
        verify(exactly = 1) { passwordsDao.insert(PASSWORD_ENTITY_3) }
        verify(exactly = 1) { oldPasswordsDao.deletePassword(1) }
        verify(exactly = 0) { oldPasswordsDao.deletePassword(2) }
        verify(exactly = 1) { oldPasswordsDao.deletePassword(3) }
    }

    private companion object {
        const val PASSWORD_SCORE = 50
        const val DECRYPTED_PASSWORD = "Password"
        val EXPIRATION_DATE: LocalDateTime = LocalDateTime.of(2023, 7, 15, 12, 0, 0)
        val OLD_PASSWORD_ENTITY = OldPasswordEntity(
            id = 1,
            platformName = ByteArray(1) { index -> index.toByte() },
            platformIV = ByteArray(1) { index -> index.toByte() },
            password = ByteArray(1) { index -> index.toByte() },
            passwordIV = ByteArray(1) { index -> index.toByte() },
            expirationDate = EXPIRATION_DATE
        )
        val OLD_PASSWORD_ENTITY_2 = OldPasswordEntity(
            id = 2,
            platformName = ByteArray(2) { index -> index.toByte() },
            platformIV = ByteArray(2) { index -> index.toByte() },
            password = ByteArray(2) { index -> index.toByte() },
            passwordIV = ByteArray(2) { index -> index.toByte() },
            expirationDate = EXPIRATION_DATE
        )
        val OLD_PASSWORD_ENTITY_3 = OldPasswordEntity(
            id = 3,
            platformName = ByteArray(3) { index -> index.toByte() },
            platformIV = ByteArray(3) { index -> index.toByte() },
            password = ByteArray(3) { index -> index.toByte() },
            passwordIV = ByteArray(3) { index -> index.toByte() },
            expirationDate = EXPIRATION_DATE
        )
        val NAMES_ENTITY = NamesEntity(
            id = 0,
            platformName = OLD_PASSWORD_ENTITY.platformName,
            platformNameIv = OLD_PASSWORD_ENTITY.platformIV
        )
        val NAMES_ENTITY_2 = NamesEntity(
            id = 0,
            platformName = OLD_PASSWORD_ENTITY_2.platformName,
            platformNameIv = OLD_PASSWORD_ENTITY_2.platformIV
        )
        val NAMES_ENTITY_3 = NamesEntity(
            id = 0,
            platformName = OLD_PASSWORD_ENTITY_3.platformName,
            platformNameIv = OLD_PASSWORD_ENTITY_3.platformIV
        )
        val PASSWORD_ENTITY = PasswordEntity(
            id = 0,
            nameId = 1,
            password = OLD_PASSWORD_ENTITY.password,
            passwordIV = OLD_PASSWORD_ENTITY.passwordIV,
            passwordScore = PASSWORD_SCORE,
            expirationDate = EXPIRATION_DATE
        )
        val PASSWORD_ENTITY_2 = PasswordEntity(
            id = 0,
            nameId = 2,
            password = OLD_PASSWORD_ENTITY_2.password,
            passwordIV = OLD_PASSWORD_ENTITY_2.passwordIV,
            passwordScore = PASSWORD_SCORE,
            expirationDate = EXPIRATION_DATE
        )
        val PASSWORD_ENTITY_3 = PasswordEntity(
            id = 0,
            nameId = 3,
            password = OLD_PASSWORD_ENTITY_3.password,
            passwordIV = OLD_PASSWORD_ENTITY_3.passwordIV,
            passwordScore = PASSWORD_SCORE,
            expirationDate = EXPIRATION_DATE
        )
    }
}
