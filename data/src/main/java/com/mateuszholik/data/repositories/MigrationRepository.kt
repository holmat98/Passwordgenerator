package com.mateuszholik.data.repositories

import com.mateuszholik.cryptography.KeyBaseEncryptionManager
import com.mateuszholik.cryptography.models.EncryptedData
import com.mateuszholik.data.db.daos.NamesDao
import com.mateuszholik.data.db.daos.OldPasswordsDao
import com.mateuszholik.data.db.daos.PasswordsDao
import com.mateuszholik.data.db.models.NamesEntity
import com.mateuszholik.data.db.models.OldPasswordEntity
import com.mateuszholik.data.db.models.PasswordEntity
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

interface MigrationRepository {

    fun migrate(calculatePasswordScore: (password: String) -> Single<Int>): Completable
}

internal class MigrationRepositoryImpl(
    private val oldPasswordsDao: OldPasswordsDao,
    private val namesDao: NamesDao,
    private val passwordsDao: PasswordsDao,
    private val encryptionManager: KeyBaseEncryptionManager,
) : MigrationRepository {

    override fun migrate(
        calculatePasswordScore: (password: String) -> Single<Int>,
    ): Completable =
        oldPasswordsDao.getAllPasswords()
            .flatMapObservable { Observable.fromIterable(it) }
            .flatMapCompletable { oldPassword ->
                Single.zip(
                    namesDao.insertAndGetId(
                        NamesEntity(
                            id = 0,
                            platformName = oldPassword.platformName,
                            platformNameIv = oldPassword.platformIV,
                        )
                    ),
                    decryptPasswordAndCalculateScore(oldPassword, calculatePasswordScore)
                ) { nameId, passwordScore ->

                    Pair(nameId, passwordScore)
                }.flatMapCompletable {
                    passwordsDao.insert(
                        PasswordEntity(
                            id = 0,
                            nameId = it.first,
                            password = oldPassword.password,
                            passwordIV = oldPassword.passwordIV,
                            passwordScore = it.second,
                            expirationDate = oldPassword.expirationDate
                        )
                    ).andThen(oldPasswordsDao.deletePassword(oldPassword.id))
                }.onErrorComplete()
            }

    private fun decryptPasswordAndCalculateScore(
        oldPasswordEntity: OldPasswordEntity,
        calculatePasswordScore: (password: String) -> Single<Int>,
    ): Single<Int> =
        Single.just(
            encryptionManager.decrypt(
                EncryptedData(
                    data = oldPasswordEntity.password,
                    iv = oldPasswordEntity.passwordIV
                )
            )
        )
            .flatMap { calculatePasswordScore(it) }
            .onErrorReturn { 0 }
}
