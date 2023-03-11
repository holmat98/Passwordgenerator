package com.mateuszholik.domain.usecase

import com.mateuszholik.cryptography.PasswordBaseEncryptionManager
import com.mateuszholik.data.repositories.PasswordsRepository
import com.mateuszholik.domain.factories.UriFactory
import com.mateuszholik.domain.mappers.PasswordsListToExportPasswordsListMapper
import com.mateuszholik.domain.models.DataToSave
import com.mateuszholik.domain.models.ExportType
import com.mateuszholik.domain.parsers.PasswordsParser
import com.mateuszholik.domain.usecase.base.CompletableUseCase
import io.reactivex.rxjava3.core.Completable
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

interface ExportPasswordsUseCase : CompletableUseCase<ExportType>

internal class ExportPasswordsUseCaseImpl(
    private val passwordsRepository: PasswordsRepository,
    private val passwordsListToExportPasswordsListMapper: PasswordsListToExportPasswordsListMapper,
    private val passwordsParser: PasswordsParser,
    private val encryptionManager: PasswordBaseEncryptionManager,
    private val saveDataToFileUseCase: SaveDataToFileUseCase,
    private val uriFactory: UriFactory
) : ExportPasswordsUseCase {

    override fun invoke(param: ExportType): Completable =
        passwordsRepository.getAllPasswords()
            .map { passwordsListToExportPasswordsListMapper.map(it) }
            .map { passwordsParser.parseToString(it) }
            .map { encryptPasswordsIfNeeded(param, it) }
            .flatMapCompletable {
                saveDataToFileUseCase(
                    DataToSave(
                        data = it,
                        uri = uriFactory.create(FILE_NAME, param is ExportType.EncryptedExport)
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
        val FORMATTER: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val FILE_NAME = "${LocalDateTime.now().format(FORMATTER)}_passwords"
    }
}
