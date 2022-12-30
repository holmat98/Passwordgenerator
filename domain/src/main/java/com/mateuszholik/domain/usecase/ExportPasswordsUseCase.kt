package com.mateuszholik.domain.usecase

import com.mateuszholik.cryptography.PasswordBaseEncryptionManager
import com.mateuszholik.data.repositories.PasswordsRepository
import com.mateuszholik.domain.factories.UriFactory
import com.mateuszholik.domain.mappers.PasswordsListToExportPasswordsListMapper
import com.mateuszholik.domain.models.DataToSave
import com.mateuszholik.domain.models.ExportType
import com.mateuszholik.domain.parsers.JsonParser
import com.mateuszholik.domain.usecase.base.CompletableUseCase
import io.reactivex.rxjava3.core.Completable
import java.time.LocalDateTime

interface ExportPasswordsUseCase : CompletableUseCase<ExportType>

internal class ExportPasswordsUseCaseImpl(
    private val passwordsRepository: PasswordsRepository,
    private val passwordsListToExportPasswordsListMapper: PasswordsListToExportPasswordsListMapper,
    private val jsonParser: JsonParser,
    private val encryptionManager: PasswordBaseEncryptionManager,
    private val saveDataToFileUseCase: SaveDataToFileUseCase,
    private val uriFactory: UriFactory
) : ExportPasswordsUseCase {

    override fun invoke(param: ExportType): Completable =
        passwordsRepository.getAllPasswords()
            .map { passwordsListToExportPasswordsListMapper.map(it) }
            .map { jsonParser.parseToJson(it) }
            .map { encryptPasswordsIfNeeded(param, it) }
            .flatMapCompletable {
                saveDataToFileUseCase(
                    DataToSave(
                        data = it,
                        uri = uriFactory.create(FILE_NAME)
                    )
                )
            }

    private fun encryptPasswordsIfNeeded(exportType: ExportType, passwords: String): String =
        if (exportType is ExportType.EncryptedExport) {
            encryptionManager.encrypt(
                password = exportType.encryptionPassword,
                dataToEncrypt = passwords
            ).toString()
        } else {
            passwords
        }

    private companion object {
        val FILE_NAME = "${LocalDateTime.now()}_passwords"
    }
}