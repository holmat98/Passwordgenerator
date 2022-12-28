package com.mateuszholik.domain.models

import android.net.Uri

sealed class ExportData(val uri: Uri) {

    data class EncryptedExport(val exportUri: Uri, val encryptionPassword: String) : ExportData(exportUri)
    data class Export(val exportUri: Uri) : ExportData(exportUri)
}
