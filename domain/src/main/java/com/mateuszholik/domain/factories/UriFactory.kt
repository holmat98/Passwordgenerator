package com.mateuszholik.domain.factories

import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import com.mateuszholik.domain.utils.ImportExportUtils

internal interface UriFactory {

    fun create(fileName: String, isEncrypted: Boolean): Uri?
}

internal class DownloadsFolderUriFactoryImpl(
    private val context: Context
) : UriFactory {

    override fun create(fileName: String, isEncrypted: Boolean): Uri? {
        val mimeType = if (isEncrypted) {
            ImportExportUtils.BINARY_DATA_MIME_TYPE
        } else {
            ImportExportUtils.PLAIN_TEXT_MIME_TYPE
        }
        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, fileName)
            put(MediaStore.MediaColumns.MIME_TYPE, mimeType)
            put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS)
        }

        return context.contentResolver.insert(
            MediaStore.Downloads.EXTERNAL_CONTENT_URI,
            contentValues
        )
    }
}
