package com.mateuszholik.domain.factories

import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import com.mateuszholik.domain.utils.ImportExportUtils

internal interface UriFactory {

    fun create(fileName: String): Uri?
}

internal class DownloadsFolderUriFactoryImpl(
    private val context: Context
) : UriFactory {

    override fun create(fileName: String): Uri? {
        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, fileName)
            put(MediaStore.MediaColumns.MIME_TYPE, ImportExportUtils.MIME_TYPE)
            put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS)
        }

        return context.contentResolver.insert(
            MediaStore.Downloads.EXTERNAL_CONTENT_URI,
            contentValues
        )
    }
}