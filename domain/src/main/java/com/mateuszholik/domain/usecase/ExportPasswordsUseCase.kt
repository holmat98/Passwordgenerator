package com.mateuszholik.domain.usecase

import android.net.Uri
import com.mateuszholik.data.repositories.PasswordsRepository
import com.mateuszholik.domain.mappers.PasswordsListToExportPasswordsListMapper
import com.mateuszholik.domain.models.DataToSave
import com.mateuszholik.domain.models.ExportData
import com.mateuszholik.domain.parsers.JsonParser
import com.mateuszholik.domain.usecase.base.CompletableUseCase
import io.reactivex.rxjava3.core.Completable

interface ExportPasswordsUseCase : CompletableUseCase<ExportData>

internal class ExportPasswordsUseCaseImpl(
    private val passwordsRepository: PasswordsRepository,
    private val passwordsListToExportPasswordsListMapper: PasswordsListToExportPasswordsListMapper,
    private val jsonParser: JsonParser,
    private val saveDataToFileUseCase: SaveDataToFileUseCase
) : ExportPasswordsUseCase {

    override fun invoke(param: ExportData): Completable =
        passwordsRepository.getAllPasswords()
            .map { passwordsListToExportPasswordsListMapper.map(it) }
            .map { jsonParser.parseToJson(it) }
            .flatMapCompletable {
                saveDataToFileUseCase.invoke(
                    DataToSave(
                        data = it,
                        uri = Uri.EMPTY
                    )
                )
            }
}