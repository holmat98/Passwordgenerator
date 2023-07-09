package com.mateuszholik.data.repositories

import com.mateuszholik.cryptography.models.EncryptedData
import com.mateuszholik.data.db.daos.OldPasswordsDao
import com.mateuszholik.data.db.models.entities.OldPasswordEntity
import com.mateuszholik.data.mappers.NewPasswordToPasswordDBMapper
import com.mateuszholik.data.mappers.NewPasswordsListToPasswordDBListMapper
import com.mateuszholik.data.mappers.PasswordDBListToPasswordListMapper
import com.mateuszholik.data.mappers.PasswordDBToPasswordMapper
import com.mateuszholik.data.mappers.UpdatedPasswordToPasswordDBMapper
import com.mateuszholik.data.repositories.models.NewPassword
import com.mateuszholik.data.repositories.models.Password
import com.mateuszholik.data.repositories.models.UpdatedPassword
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Single
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

class OldPasswordsRepositoryImplTest {

    private val oldPasswordsDao = mockk<OldPasswordsDao> {
        every { insertAndGetId(PASSWORD_DB) } returns Single.just(ID)
        every { insertPasswords(listOf(PASSWORD_DB, PASSWORD_DB_2)) } returns Completable.complete()
        every { update(PASSWORD_DB) } returns Completable.complete()
        every { deletePassword(MAPPED_PASSWORD.id) } returns Completable.complete()
        every { getPassword(ID) } returns Maybe.just(PASSWORD_DB)
        every { getAllPasswords() } returns Single.just(listOf(PASSWORD_DB))
    }

    private val passwordDBListToPasswordListMapper = mockk<PasswordDBListToPasswordListMapper> {
        every { map(listOf(PASSWORD_DB)) } returns listOf(MAPPED_PASSWORD)
    }

    private val passwordDBToPasswordMapper = mockk<PasswordDBToPasswordMapper> {
        every { map(PASSWORD_DB) } returns MAPPED_PASSWORD
    }

    private val newPasswordToPasswordDBMapper = mockk<NewPasswordToPasswordDBMapper> {
        every { map(NEW_PASSWORD) } returns PASSWORD_DB
    }

    private val newPasswordsListToPasswordDBListMapper =
        mockk<NewPasswordsListToPasswordDBListMapper> {
            every { map(listOf(NEW_PASSWORD, NEW_PASSWORD_2)) } returns listOf(
                PASSWORD_DB,
                PASSWORD_DB_2
            )
        }

    private val updatedPasswordToPasswordDBMapper = mockk<UpdatedPasswordToPasswordDBMapper> {
        every { map(UPDATED_PASSWORD) } returns PASSWORD_DB
    }


    private val passwordsRepository = OldPasswordsRepositoryImpl(
        oldPasswordsDao = oldPasswordsDao,
        passwordDBListToPasswordListMapper = passwordDBListToPasswordListMapper,
        passwordDBToPasswordMapper = passwordDBToPasswordMapper,
        newPasswordToPasswordDBMapper = newPasswordToPasswordDBMapper,
        newPasswordsListToPasswordDBListMapper = newPasswordsListToPasswordDBListMapper,
        updatedPasswordToPasswordDBMapper = updatedPasswordToPasswordDBMapper
    )

    @Test
    fun `Password is saved correctly to the database and its id is returned`() {
        passwordsRepository.insertAndGetId(NEW_PASSWORD)
            .test()
            .assertValue(ID)

        verify(exactly = 1) { oldPasswordsDao.insertAndGetId(PASSWORD_DB) }
    }

    @Test
    fun `List of passwords is saved correctly to the database`() {
        passwordsRepository.insertPasswords(listOf(NEW_PASSWORD, NEW_PASSWORD_2))
            .test()
            .assertComplete()

        verify(exactly = 1) {
            oldPasswordsDao.insertPasswords(listOf(PASSWORD_DB, PASSWORD_DB_2))
        }
    }

    @Test
    fun `Password is updated correctly in the database`() {
        passwordsRepository.update(UPDATED_PASSWORD)
            .test()
            .assertComplete()

        verify(exactly = 1) { oldPasswordsDao.update(PASSWORD_DB) }
    }

    @Test
    fun `Password is deleted correctly`() {
        passwordsRepository.delete(MAPPED_PASSWORD.id)
            .test()
            .assertComplete()

        verify(exactly = 1) {
            oldPasswordsDao.deletePassword(MAPPED_PASSWORD.id)
        }
    }

    @Test
    fun `Password with given id is correctly provided and mapper to Password object`() {
        passwordsRepository.getPassword(ID)
            .test()
            .assertValue(MAPPED_PASSWORD)

        verify(exactly = 1) { oldPasswordsDao.getPassword(ID) }
    }

    @Test
    fun `When dao does not return password, repository will return empty also`() {
        every {
            oldPasswordsDao.getPassword(ID_2)
        } returns Maybe.empty()

        passwordsRepository.getPassword(ID_2)
            .test()
            .assertNoValues()

        verify(exactly = 1) { oldPasswordsDao.getPassword(ID_2) }
    }

    @Test
    fun `All passwords are correctly provided and mapped to list of Password objects`() {
        passwordsRepository.getAllPasswords()
            .test()
            .assertValue(listOf(MAPPED_PASSWORD))

        verify(exactly = 1) {
            oldPasswordsDao.getAllPasswords()
        }
    }

    private companion object {
        const val ID = 1L
        const val ID_2 = 2L
        const val PLATFORM_NAME = "platform"
        const val PLATFORM_NAME_2 = "platform2"
        const val PASSWORD = "password"
        const val PASSWORD_2 = "password2"
        val NEW_PASSWORD = NewPassword(
            platformName = PLATFORM_NAME,
            password = PASSWORD
        )
        val NEW_PASSWORD_2 = NewPassword(
            platformName = PLATFORM_NAME_2,
            password = PASSWORD_2
        )
        val UPDATED_PASSWORD = UpdatedPassword(
            id = ID,
            platformName = PLATFORM_NAME,
            password = PASSWORD
        )
        val ENCRYPTED_PLATFORM = EncryptedData(
            iv = ByteArray(10),
            data = ByteArray(11)
        )
        val ENCRYPTED_PASSWORD = EncryptedData(
            iv = ByteArray(12),
            data = ByteArray(13)
        )
        val ENCRYPTED_PLATFORM_2 = EncryptedData(
            iv = ByteArray(14),
            data = ByteArray(15)
        )
        val ENCRYPTED_PASSWORD_2 = EncryptedData(
            iv = ByteArray(16),
            data = ByteArray(17)
        )
        val EXPIRING_DATE: LocalDateTime = LocalDateTime.of(2022, 6, 11, 12, 0, 0)
        val PASSWORD_DB = OldPasswordEntity(
            id = ID,
            platformName = ENCRYPTED_PLATFORM.data,
            platformIV = ENCRYPTED_PLATFORM.iv,
            password = ENCRYPTED_PASSWORD.data,
            passwordIV = ENCRYPTED_PASSWORD.iv,
            expirationDate = EXPIRING_DATE
        )
        val PASSWORD_DB_2 = OldPasswordEntity(
            id = ID_2,
            platformName = ENCRYPTED_PLATFORM_2.data,
            platformIV = ENCRYPTED_PLATFORM_2.iv,
            password = ENCRYPTED_PASSWORD_2.data,
            passwordIV = ENCRYPTED_PASSWORD_2.iv,
            expirationDate = EXPIRING_DATE
        )
        val MAPPED_PASSWORD = Password(
            id = ID,
            platformName = PLATFORM_NAME,
            password = PASSWORD,
            expiringDate = EXPIRING_DATE
        )
    }
}
