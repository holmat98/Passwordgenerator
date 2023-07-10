package com.mateuszholik.data.repositories

import com.mateuszholik.data.db.daos.PasswordsDao
import com.mateuszholik.data.mappers.NewPasswordToPasswordDBMapper
import com.mateuszholik.data.mappers.NewPasswordsListToPasswordDBListMapper
import com.mateuszholik.data.mappers.PasswordInfoViewListToPasswordInfoListMapper
import com.mateuszholik.data.mappers.PasswordInfoViewToPasswordInfoMapper
import com.mateuszholik.data.mappers.UpdatedPasswordToPasswordDBMapper
import com.mateuszholik.data.repositories.models.NewPassword
import com.mateuszholik.data.repositories.models.PasswordInfo
import com.mateuszholik.data.repositories.models.UpdatedPassword
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Single

interface PasswordsRepository {

    fun insertAndGetId(newPassword: NewPassword): Single<Long>

    fun insertPasswords(newPasswords: List<NewPassword>): Completable

    fun delete(passwordId: Long): Completable

    fun update(updatedPassword: UpdatedPassword): Completable

    fun getPassword(passwordId: Long): Maybe<PasswordInfo>

    fun getAllPasswords(): Single<List<PasswordInfo>>
}

internal class PasswordsRepositoryImpl(
    private val passwordsDao: PasswordsDao,
    private val passwordInfoViewListToPasswordInfoListMapper: PasswordInfoViewListToPasswordInfoListMapper,
    private val passwordInfoViewToPasswordInfoMapper: PasswordInfoViewToPasswordInfoMapper,
    private val newPasswordToPasswordDBMapper: NewPasswordToPasswordDBMapper,
    private val newPasswordsListToPasswordDBListMapper: NewPasswordsListToPasswordDBListMapper,
    private val updatedPasswordToPasswordDBMapper: UpdatedPasswordToPasswordDBMapper,
) : PasswordsRepository {

    override fun insertAndGetId(newPassword: NewPassword): Single<Long> =
        Single.just(1)
        /*Single.just(newPasswordToPasswordDBMapper.map(newPassword))
            .flatMap { passwordsDao.insertAndGetId(it) }*/

    override fun insertPasswords(newPasswords: List<NewPassword>): Completable =
        Completable.complete()
        /*Single.just(newPasswordsListToPasswordDBListMapper.map(newPasswords))
            .flatMapCompletable { passwordsDao.insertPasswords(it) }*/

    override fun delete(passwordId: Long): Completable =
        passwordsDao.delete(passwordId)

    override fun update(updatedPassword: UpdatedPassword): Completable =
        Completable.complete()
        /*Single.just(updatedPasswordToPasswordDBMapper.map(updatedPassword))
            .flatMapCompletable { passwordsDao.update(it) }*/

    override fun getPassword(passwordId: Long): Maybe<PasswordInfo> =
        Maybe.empty()
        /*passwordsDao.getPassword(passwordId)
            .map { passwordDBToPasswordMapper.map(it) }*/
    override fun getAllPasswords(): Single<List<PasswordInfo>> =
        passwordsDao.getAllPasswordsInfo()
            .map { passwordInfoViewListToPasswordInfoListMapper.map(it) }
}
