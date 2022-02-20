package com.mateuszholik.data.repositories

import com.mateuszholik.data.db.daos.PasswordsDao
import com.mateuszholik.data.mappers.PasswordDBMapper
import com.mateuszholik.data.mappers.PasswordListMapper
import com.mateuszholik.data.repositories.models.Password
import com.mateuszholik.data.repositories.models.Resource
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

internal class PasswordsRepositoryImpl(
    private val passwordsDao: PasswordsDao,
    private val passwordDBMapper: PasswordDBMapper,
    private val passwordListMapper: PasswordListMapper
) : BaseRepository(), PasswordsRepository {

    override fun insert(password: Password): Completable =
        passwordsDao.insert(passwordDBMapper.map(password))

    override fun delete(password: Password): Completable =
        passwordsDao.delete(passwordDBMapper.map(password))

    override fun update(password: Password): Completable =
        passwordsDao.update(passwordDBMapper.map(password))

    override fun getAllPasswords(): Single<Resource<List<Password>>> =
        passwordsDao.getAllPasswords()
            .map {
                createResource(it) { list ->
                    passwordListMapper.map(list)
                }
            }
}