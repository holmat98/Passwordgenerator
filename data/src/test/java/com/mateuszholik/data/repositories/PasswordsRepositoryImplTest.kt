package com.mateuszholik.data.repositories

import com.mateuszholik.cryptography.models.EncryptedData
import com.mateuszholik.data.db.daos.NamesDao
import com.mateuszholik.data.db.daos.PasswordsDao
import com.mateuszholik.data.db.models.PasswordDB
import com.mateuszholik.data.db.models.entities.NamesEntity
import com.mateuszholik.data.db.models.entities.PasswordEntity
import com.mateuszholik.data.db.models.views.PasswordDetailsView
import com.mateuszholik.data.db.models.views.PasswordInfoView
import com.mateuszholik.data.mappers.NewPasswordToNamesEntityMapper
import com.mateuszholik.data.mappers.NewPasswordToPasswordEntityMapper
import com.mateuszholik.data.mappers.PasswordDetailsViewToPasswordDetailsMapper
import com.mateuszholik.data.mappers.PasswordInfoViewListToPasswordInfoListMapper
import com.mateuszholik.data.mappers.PasswordsDBListToPasswordsListMapper
import com.mateuszholik.data.mappers.UpdatedPasswordToPasswordEntityMapper
import com.mateuszholik.data.mappers.UpdatedPasswordToUpdatedNamesMapper
import com.mateuszholik.data.repositories.models.NewPassword
import com.mateuszholik.data.repositories.models.Password
import com.mateuszholik.data.repositories.models.PasswordDetails
import com.mateuszholik.data.repositories.models.PasswordInfo
import com.mateuszholik.data.repositories.models.PasswordValidity
import com.mateuszholik.data.repositories.models.UpdatedPassword
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Single
import org.junit.jupiter.api.DynamicTest.dynamicTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory
import java.time.LocalDateTime

class PasswordsRepositoryImplTest {

    private val passwordsDao = mockk<PasswordsDao> {
        every { insertAndGetId(PASSWORD_ENTITY) } returns Single.just(ID)
        every { insertAndGetId(PASSWORD_ENTITY_2) } returns Single.just(ID_2)
        every { insertAndGetId(PASSWORD_ENTITY_3) } returns Single.just(ID_3)
        every { getNameIdFor(ID) } returns Maybe.just(ID)
    }

    private val namesDao = mockk<NamesDao> {
        every { insertAndGetId(NAMES_ENTITY) } returns Single.just(ID)
        every { insertAndGetId(NAMES_ENTITY_2) } returns Single.just(ID_2)
        every { insertAndGetId(NAMES_ENTITY_3) } returns Single.just(ID_3)
    }

    private val passwordInfoViewListToPasswordInfoListMapper =
        mockk<PasswordInfoViewListToPasswordInfoListMapper>()

    private val passwordDetailsViewToPasswordDetailsMapper =
        mockk<PasswordDetailsViewToPasswordDetailsMapper>()

    private val newPasswordToNamesEntityMapper = mockk<NewPasswordToNamesEntityMapper> {
        every { map(NEW_PASSWORD) } returns NAMES_ENTITY
        every { map(NEW_PASSWORD_2) } returns NAMES_ENTITY_2
        every { map(NEW_PASSWORD_3) } returns NAMES_ENTITY_3
    }

    private val newPasswordToPasswordEntityMapper = mockk<NewPasswordToPasswordEntityMapper> {
        every {
            map(
                NewPasswordToPasswordEntityMapper.Param(
                    password = NEW_PASSWORD.password,
                    nameId = ID,
                    passwordScore = NEW_PASSWORD.passwordScore,
                    isExpiring = NEW_PASSWORD.isExpiring
                )
            )
        } returns PASSWORD_ENTITY
        every {
            map(
                NewPasswordToPasswordEntityMapper.Param(
                    password = NEW_PASSWORD_2.password,
                    nameId = ID_2,
                    passwordScore = NEW_PASSWORD_2.passwordScore,
                    isExpiring = NEW_PASSWORD_2.isExpiring
                )
            )
        } returns PASSWORD_ENTITY_2
        every {
            map(
                NewPasswordToPasswordEntityMapper.Param(
                    password = NEW_PASSWORD_3.password,
                    nameId = ID_3,
                    passwordScore = NEW_PASSWORD_3.passwordScore,
                    isExpiring = NEW_PASSWORD_3.isExpiring
                )
            )
        } returns PASSWORD_ENTITY_3
    }

    private val updatedPasswordToUpdatedNamesMapper = mockk<UpdatedPasswordToUpdatedNamesMapper>()

    private val updatedPasswordToPasswordEntityMapper =
        mockk<UpdatedPasswordToPasswordEntityMapper>()

    private val passwordsDBListToPasswordsListMapper = mockk<PasswordsDBListToPasswordsListMapper>()


    private val passwordsRepository = PasswordsRepositoryImpl(
        passwordsDao = passwordsDao,
        namesDao = namesDao,
        passwordInfoViewListToPasswordInfoListMapper = passwordInfoViewListToPasswordInfoListMapper,
        passwordDetailsViewToPasswordDetailsMapper = passwordDetailsViewToPasswordDetailsMapper,
        newPasswordToNamesEntityMapper = newPasswordToNamesEntityMapper,
        newPasswordToPasswordEntityMapper = newPasswordToPasswordEntityMapper,
        updatedPasswordToUpdatedNamesMapper = updatedPasswordToUpdatedNamesMapper,
        updatedPasswordToPasswordEntityMapper = updatedPasswordToPasswordEntityMapper,
        passwordsDBListToPasswordsListMapper = passwordsDBListToPasswordsListMapper
    )

    @TestFactory
    fun checkIfNewPasswordIsCorrectlySavedToTheDatabase() =
        listOf(
            NEW_PASSWORD,
            NEW_PASSWORD_2,
            NEW_PASSWORD_3
        ).mapIndexed { index, newPassword ->
            val id = index + 1L

            dynamicTest("When website and isExpiring is equal to ${newPassword.website} and ${newPassword.isExpiring} then NewPassword is correctly saved to the database") {
                passwordsRepository.insertAndGetId(newPassword)
                    .test()
                    .assertComplete()
                    .assertValue(id)

                verify { newPasswordToNamesEntityMapper.map(newPassword) }
                verify { namesDao.insertAndGetId(any()) }
                verify { newPasswordToPasswordEntityMapper.map(any()) }
                verify { passwordsDao.insertAndGetId(any()) }
            }
        }

    @Test
    fun `When password is deleted names object related to the password is also deleted`() {
        every { passwordsDao.delete(ID) } returns Completable.complete()
        every { namesDao.deleteFor(ID) } returns Completable.complete()

        passwordsRepository.delete(ID)
            .test()
            .assertComplete()

        verify(exactly = 1) { passwordsDao.getNameIdFor(ID) }
        verify(exactly = 1) { passwordsDao.delete(ID) }
        verify(exactly = 1) { namesDao.deleteFor(ID) }
    }

    @Test
    fun `When namesDao deleteFor returns with error while deleting the password the Complete is called`() {
        every { passwordsDao.delete(ID) } returns Completable.complete()
        every { namesDao.deleteFor(ID) } returns Completable.error(Exception("Something went wrong"))

        passwordsRepository.delete(ID)
            .test()
            .assertComplete()

        verify(exactly = 1) { passwordsDao.getNameIdFor(ID) }
        verify(exactly = 1) { passwordsDao.delete(ID) }
        verify(exactly = 1) { namesDao.deleteFor(ID) }
    }

    @Test
    fun `Password is correctly updated`() {
        val updatedName = UpdatedPasswordToUpdatedNamesMapper.UpdatedName(
            name = ENCRYPTED_PLATFORM.data,
            nameIv = ENCRYPTED_PLATFORM.iv,
            website = ENCRYPTED_WEBSITE.data,
            websiteIv = ENCRYPTED_WEBSITE.iv
        )

        every {
            updatedPasswordToUpdatedNamesMapper.map(
                UpdatedPasswordToUpdatedNamesMapper.UpdatedPassword(
                    name = UPDATED_PASSWORD.platformName,
                    website = UPDATED_PASSWORD.website
                )
            )
        } returns updatedName

        every {
            namesDao.update(
                id = ID,
                name = updatedName.name,
                nameIv = updatedName.nameIv,
                website = updatedName.website,
                websiteIv = updatedName.websiteIv
            )
        } returns Completable.complete()

        every {
            updatedPasswordToPasswordEntityMapper.map(
                UpdatedPasswordToPasswordEntityMapper.Param(
                    id = UPDATED_PASSWORD.id,
                    password = UPDATED_PASSWORD.password,
                    passwordScore = UPDATED_PASSWORD.passwordScore,
                    isExpiring = UPDATED_PASSWORD.isExpiring,
                    nameId = ID
                )
            )
        } returns PASSWORD_ENTITY

        every { passwordsDao.update(PASSWORD_ENTITY) } returns Completable.complete()

        passwordsRepository.update(UPDATED_PASSWORD)
            .test()
            .assertComplete()

        verify(exactly = 1) { passwordsDao.getNameIdFor(ID) }
        verify(exactly = 1) {
            updatedPasswordToUpdatedNamesMapper.map(
                UpdatedPasswordToUpdatedNamesMapper.UpdatedPassword(
                    name = UPDATED_PASSWORD.platformName,
                    website = UPDATED_PASSWORD.website
                )
            )
        }
        verify(exactly = 1) {
            namesDao.update(
                id = ID,
                name = updatedName.name,
                nameIv = updatedName.nameIv,
                website = updatedName.website,
                websiteIv = updatedName.websiteIv
            )
        }
        verify(exactly = 1) {
            updatedPasswordToPasswordEntityMapper.map(
                UpdatedPasswordToPasswordEntityMapper.Param(
                    id = UPDATED_PASSWORD.id,
                    password = UPDATED_PASSWORD.password,
                    passwordScore = UPDATED_PASSWORD.passwordScore,
                    isExpiring = UPDATED_PASSWORD.isExpiring,
                    nameId = ID
                )
            )
        }
        verify(exactly = 1) { passwordsDao.update(PASSWORD_ENTITY) }
    }

    @Test
    fun `PasswordDetails is correctly returned from getPasswordDetails`() {
        every { passwordsDao.getPasswordDetailsFor(ID) } returns Maybe.just(PASSWORD_DETAILS_VIEW)
        every {
            passwordDetailsViewToPasswordDetailsMapper.map(PASSWORD_DETAILS_VIEW)
        } returns PASSWORD_DETAILS

        passwordsRepository.getPasswordDetails(ID)
            .test()
            .assertComplete()
            .assertValue(PASSWORD_DETAILS)
    }

    @Test
    fun `When password does not exists Maybe complete is returned`() {
        every { passwordsDao.getPasswordDetailsFor(ID) } returns Maybe.empty()

        passwordsRepository.getPasswordDetails(ID)
            .test()
            .assertNoValues()

        verify(exactly = 0) { passwordDetailsViewToPasswordDetailsMapper.map(any()) }
    }

    @Test
    fun `All passwords info are correctly downloaded from database`() {
        val infoViewsList = listOf(
            PASSWORD_INFO_VIEW,
            PASSWORD_INFO_VIEW_2
        )

        every {
            passwordsDao.getAllPasswordsInfo()
        } returns Single.just(infoViewsList)

        every {
            passwordInfoViewListToPasswordInfoListMapper.map(infoViewsList)
        } returns listOf(PASSWORD_INFO, PASSWORD_INFO_2)

        passwordsRepository.getAllPasswordsInfo()
            .test()
            .assertComplete()
            .assertValue(listOf(PASSWORD_INFO, PASSWORD_INFO_2))

    }

    @Test
    fun `All passwords are correctly downloaded from database`() {
        val passwordsDBList = listOf(
            PASSWORD_DB,
            PASSWORD_DB_2
        )

        every {
            passwordsDao.getPasswords()
        } returns Single.just(passwordsDBList)

        every {
            passwordsDBListToPasswordsListMapper.map(passwordsDBList)
        } returns listOf(PASSWORD_MODEL, PASSWORD_MODEL_2)

        passwordsRepository.getPasswords()
            .test()
            .assertComplete()
            .assertValue(listOf(PASSWORD_MODEL, PASSWORD_MODEL_2))

    }

    private companion object {
        const val ID = 1L
        const val ID_2 = 2L
        const val ID_3 = 3L
        const val PLATFORM_NAME = "platform"
        const val PLATFORM_NAME_2 = "platform2"
        const val PLATFORM_NAME_3 = "platform2"
        const val PASSWORD = "password"
        const val PASSWORD_2 = "password2"
        const val PASSWORD_3 = "password2"
        const val WEBSITE = "website"
        const val PASSWORD_SCORE = 50
        val NEW_PASSWORD = NewPassword(
            platformName = PLATFORM_NAME,
            password = PASSWORD,
            website = WEBSITE,
            passwordScore = PASSWORD_SCORE,
            isExpiring = true
        )
        val NEW_PASSWORD_2 = NewPassword(
            platformName = PLATFORM_NAME_2,
            password = PASSWORD_2,
            website = null,
            passwordScore = PASSWORD_SCORE,
            isExpiring = true
        )
        val NEW_PASSWORD_3 = NewPassword(
            platformName = PLATFORM_NAME_3,
            password = PASSWORD_3,
            website = WEBSITE,
            passwordScore = PASSWORD_SCORE,
            isExpiring = false
        )
        val UPDATED_PASSWORD = UpdatedPassword(
            id = ID,
            platformName = PLATFORM_NAME,
            password = PASSWORD,
            website = WEBSITE,
            passwordScore = PASSWORD_SCORE,
            isExpiring = false
        )
        val ENCRYPTED_WEBSITE = EncryptedData(
            iv = ByteArray(8),
            data = ByteArray(9)
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
        val ENCRYPTED_PASSWORD_3 = EncryptedData(
            iv = ByteArray(20),
            data = ByteArray(21)
        )
        val EXPIRATION_DATE: LocalDateTime = LocalDateTime.of(2022, 6, 11, 12, 0, 0)
        val NAMES_ENTITY = NamesEntity(
            id = ID,
            platformName = ENCRYPTED_PLATFORM.data,
            platformNameIv = ENCRYPTED_PLATFORM.iv,
            website = ENCRYPTED_WEBSITE.data,
            websiteIv = ENCRYPTED_WEBSITE.iv
        )
        val NAMES_ENTITY_2 = NamesEntity(
            id = ID_2,
            platformName = ENCRYPTED_PLATFORM.data,
            platformNameIv = ENCRYPTED_PLATFORM.iv
        )
        val NAMES_ENTITY_3 = NamesEntity(
            id = ID_3,
            platformName = ENCRYPTED_PLATFORM.data,
            platformNameIv = ENCRYPTED_PLATFORM.iv,
            website = ENCRYPTED_WEBSITE.data,
            websiteIv = ENCRYPTED_WEBSITE.iv
        )
        val PASSWORD_ENTITY = PasswordEntity(
            id = ID,
            nameId = ID,
            password = ENCRYPTED_PASSWORD.data,
            passwordIV = ENCRYPTED_PASSWORD.iv,
            expirationDate = EXPIRATION_DATE,
            passwordScore = PASSWORD_SCORE
        )
        val PASSWORD_ENTITY_2 = PasswordEntity(
            id = ID_2,
            nameId = ID_2,
            password = ENCRYPTED_PASSWORD_2.data,
            passwordIV = ENCRYPTED_PASSWORD_2.iv,
            expirationDate = EXPIRATION_DATE,
            passwordScore = PASSWORD_SCORE
        )
        val PASSWORD_ENTITY_3 = PasswordEntity(
            id = ID_3,
            nameId = ID_3,
            password = ENCRYPTED_PASSWORD_3.data,
            passwordIV = ENCRYPTED_PASSWORD_3.iv,
            expirationDate = null,
            passwordScore = PASSWORD_SCORE
        )
        val PASSWORD_DETAILS_VIEW = PasswordDetailsView(
            id = ID,
            platformName = ENCRYPTED_PLATFORM.data,
            platformNameIv = ENCRYPTED_PLATFORM.iv,
            password = ENCRYPTED_PASSWORD.data,
            passwordIv = ENCRYPTED_PASSWORD.iv,
            website = ENCRYPTED_WEBSITE.data,
            websiteIv = ENCRYPTED_WEBSITE.iv,
            passwordScore = PASSWORD_SCORE,
            expirationDate = EXPIRATION_DATE
        )
        val PASSWORD_DETAILS = PasswordDetails(
            id = ID,
            platformName = PLATFORM_NAME,
            password = PASSWORD,
            website = WEBSITE,
            passwordScore = PASSWORD_SCORE,
            passwordValidity = PasswordValidity.Valid(EXPIRATION_DATE)
        )
        val PASSWORD_INFO_VIEW = PasswordInfoView(
            id = ID,
            platformName = ENCRYPTED_PLATFORM.data,
            platformNameIv = ENCRYPTED_PLATFORM.iv,
            passwordScore = PASSWORD_SCORE,
            expirationDate = EXPIRATION_DATE
        )
        val PASSWORD_INFO_VIEW_2 = PasswordInfoView(
            id = ID_2,
            platformName = ENCRYPTED_PLATFORM_2.data,
            platformNameIv = ENCRYPTED_PLATFORM_2.iv,
            passwordScore = PASSWORD_SCORE,
            expirationDate = null
        )
        val PASSWORD_INFO = PasswordInfo(
            id = ID,
            platformName = PLATFORM_NAME,
            passwordScore = PASSWORD_SCORE,
            passwordValidity = PasswordValidity.Valid(EXPIRATION_DATE)
        )
        val PASSWORD_INFO_2 = PasswordInfo(
            id = ID_2,
            platformName = PLATFORM_NAME_2,
            passwordScore = PASSWORD_SCORE,
            passwordValidity = PasswordValidity.NeverExpires
        )
        val PASSWORD_DB = PasswordDB(
            platformName = ENCRYPTED_PLATFORM.data,
            platformNameIv = ENCRYPTED_PLATFORM.iv,
            password = ENCRYPTED_PASSWORD.data,
            passwordIv = ENCRYPTED_PASSWORD.iv
        )
        val PASSWORD_DB_2 = PasswordDB(
            platformName = ENCRYPTED_PLATFORM_2.data,
            platformNameIv = ENCRYPTED_PLATFORM_2.iv,
            password = ENCRYPTED_PASSWORD_2.data,
            passwordIv = ENCRYPTED_PASSWORD_2.iv
        )
        val PASSWORD_MODEL = Password(
            platformName = PLATFORM_NAME,
            password = PASSWORD
        )
        val PASSWORD_MODEL_2 = Password(
            platformName = PLATFORM_NAME_2,
            password = PASSWORD_2
        )
    }
}
