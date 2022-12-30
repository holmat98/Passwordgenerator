package com.mateuszholik.domain.models

sealed class ExportType {

    data class EncryptedExport(val encryptionPassword: String) : ExportType()
    object Export : ExportType()
}
