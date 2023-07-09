package com.mateuszholik.domain.usecase

import com.mateuszholik.cryptography.PasswordBaseEncryptionManager
import com.mateuszholik.cryptography.extensions.toEncryptedData
import com.mateuszholik.data.repositories.OldPasswordsRepository
import com.mateuszholik.domain.mappers.ExportedPasswordsListToNewPasswordsListMapper
import com.mateuszholik.domain.models.ImportType
import com.mateuszholik.domain.parsers.PasswordsParser
import com.mateuszholik.domain.usecase.base.CompletableParameterizedUseCase
import io.reactivex.rxjava3.core.Completable

interface ImportPasswordsUseCase : CompletableParameterizedUseCase<ImportType>

internal class ImportPasswordsUseCaseImpl(
    private val readDataFromFileUseCase: ReadDataFromFileUseCase,
    private val encryptionManager: PasswordBaseEncryptionManager,
    private val passwordsParser: PasswordsParser,
    private val exportedPasswordsMapper: ExportedPasswordsListToNewPasswordsListMapper,
    private val oldPasswordsRepository: OldPasswordsRepository
) : ImportPasswordsUseCase {

    override fun invoke(param: ImportType): Completable =
        readDataFromFileUseCase(param.uri)
            .map { decryptImportedDataIfNeeded(param, it) }
            .map { passwordsParser.parseFromString(it) }
            .map { exportedPasswordsMapper.map(it) }
            .flatMapCompletable { oldPasswordsRepository.insertPasswords(it) }

    private fun decryptImportedDataIfNeeded(importType: ImportType, importedData: String): String =
        if (importType is ImportType.EncryptedImport) {
            encryptionManager.decrypt(
                password = importType.encryptionPassword,
                encryptedData = importedData.toEncryptedData()
            )
        } else {
            importedData
        }
}
