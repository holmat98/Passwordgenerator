package com.mateuszholik.data.repositories

import com.mateuszholik.cryptography.KeyBaseEncryptionManager
import com.mateuszholik.data.db.daos.NamesDao
import com.mateuszholik.data.db.daos.PasswordsDao
import com.mateuszholik.data.mappers.AutofillPasswordsDetailsViewListMapper
import com.mateuszholik.data.mappers.NewPasswordToNamesEntityMapper
import com.mateuszholik.data.mappers.NewPasswordToPasswordEntityMapper
import com.mateuszholik.data.mappers.PasswordDetailsViewToPasswordDetailsMapper
import com.mateuszholik.data.mappers.PasswordInfoViewListToPasswordInfoListMapper
import com.mateuszholik.data.mappers.PasswordsDBListToPasswordsListMapper
import com.mateuszholik.data.mappers.UpdatedPasswordToPasswordEntityMapper
import com.mateuszholik.data.mappers.UpdatedPasswordToUpdatedNamesMapper
import com.mateuszholik.data.repositories.models.AutofillPasswordDetails
import com.mateuszholik.data.repositories.models.NewPassword
import com.mateuszholik.data.repositories.models.Password
import com.mateuszholik.data.repositories.models.PasswordDetails
import com.mateuszholik.data.repositories.models.PasswordInfo
import com.mateuszholik.data.repositories.models.UpdatedPassword
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Single

interface PasswordsRepository {

    fun insertAndGetId(newPassword: NewPassword): Single<Long>

    fun delete(passwordId: Long): Completable

    fun update(updatedPassword: UpdatedPassword): Completable

    fun updatePackageName(id: Long, packageName: String): Completable

    fun getPasswordDetails(passwordId: Long): Maybe<PasswordDetails>

    fun getAllPasswordsInfo(): Single<List<PasswordInfo>>

    fun getPasswords(): Single<List<Password>>

    fun getAutofillPasswordsDetails(): Single<List<AutofillPasswordDetails>>
}

internal class PasswordsRepositoryImpl(
    private val passwordsDao: PasswordsDao,
    private val namesDao: NamesDao,
    private val passwordInfoViewListToPasswordInfoListMapper: PasswordInfoViewListToPasswordInfoListMapper,
    private val passwordDetailsViewToPasswordDetailsMapper: PasswordDetailsViewToPasswordDetailsMapper,
    private val newPasswordToNamesEntityMapper: NewPasswordToNamesEntityMapper,
    private val newPasswordToPasswordEntityMapper: NewPasswordToPasswordEntityMapper,
    private val updatedPasswordToUpdatedNamesMapper: UpdatedPasswordToUpdatedNamesMapper,
    private val updatedPasswordToPasswordEntityMapper: UpdatedPasswordToPasswordEntityMapper,
    private val passwordsDBListToPasswordsListMapper: PasswordsDBListToPasswordsListMapper,
    private val autofillPasswordsDetailsViewListMapper: AutofillPasswordsDetailsViewListMapper,
    private val encryptionManager: KeyBaseEncryptionManager,
) : PasswordsRepository {

    override fun insertAndGetId(newPassword: NewPassword): Single<Long> =
        Single.just(newPasswordToNamesEntityMapper.map(newPassword))
            .flatMap { namesDao.insertAndGetId(it) }
            .map {
                newPasswordToPasswordEntityMapper.map(
                    NewPasswordToPasswordEntityMapper.Param(
                        password = newPassword.password,
                        nameId = it,
                        passwordScore = newPassword.passwordScore,
                        isExpiring = newPassword.isExpiring
                    )
                )
            }
            .flatMap { passwordsDao.insertAndGetId(it) }

    override fun delete(passwordId: Long): Completable =
        passwordsDao.getNameIdFor(passwordId)
            .flatMapCompletable {
                passwordsDao.delete(passwordId)
                    .andThen(
                        namesDao
                            .deleteFor(it)
                            .onErrorComplete()
                    )
            }

    override fun update(updatedPassword: UpdatedPassword): Completable =
        passwordsDao.getNameIdFor(updatedPassword.id)
            .flatMapSingle {
                val updatedName = updatedPasswordToUpdatedNamesMapper.map(
                    UpdatedPasswordToUpdatedNamesMapper.UpdatedPassword(
                        name = updatedPassword.platformName,
                        website = updatedPassword.website
                    )
                )

                namesDao.update(
                    id = it,
                    name = updatedName.name,
                    nameIv = updatedName.nameIv,
                    website = updatedName.website,
                    websiteIv = updatedName.websiteIv
                ).andThen(Single.just(it))
            }.flatMapCompletable {
                val passwordEntity = updatedPasswordToPasswordEntityMapper.map(
                    UpdatedPasswordToPasswordEntityMapper.Param(
                        id = updatedPassword.id,
                        password = updatedPassword.password,
                        passwordScore = updatedPassword.passwordScore,
                        isExpiring = updatedPassword.isExpiring,
                        nameId = it
                    )
                )

                passwordsDao.update(passwordEntity)
            }

    override fun updatePackageName(id: Long, packageName: String): Completable =
        passwordsDao.getNameIdFor(id)
            .flatMapCompletable { nameId ->
                val encryptedPackageName = encryptionManager.encrypt(packageName)

                namesDao.updatePackageNameFor(
                    id = nameId,
                    packageName = encryptedPackageName.data,
                    packageNameIv = encryptedPackageName.iv
                )
            }

    override fun getPasswordDetails(passwordId: Long): Maybe<PasswordDetails> =
        passwordsDao.getPasswordDetailsFor(passwordId)
            .map { passwordDetailsViewToPasswordDetailsMapper.map(it) }

    override fun getAllPasswordsInfo(): Single<List<PasswordInfo>> =
        passwordsDao.getAllPasswordsInfo()
            .map { passwordInfoViewListToPasswordInfoListMapper.map(it) }

    override fun getPasswords(): Single<List<Password>> =
        passwordsDao.getPasswords()
            .map { passwordsDBListToPasswordsListMapper.map(it) }

    override fun getAutofillPasswordsDetails(): Single<List<AutofillPasswordDetails>> =
        passwordsDao.getAllAutofillPasswordDetails().map {
            autofillPasswordsDetailsViewListMapper.map(it)
        }
}
