package com.mateuszholik.domain.models

import android.net.Uri

sealed class ImportType(val uri: Uri) {

    data class EncryptedImport(
        val fileUri: Uri,
        val encryptionPassword: String
    ) : ImportType(fileUri)

    data class Import(val fileUri: Uri) : ImportType(fileUri)
}
