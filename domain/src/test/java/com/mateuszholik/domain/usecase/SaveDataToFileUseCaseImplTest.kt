package com.mateuszholik.domain.usecase

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import com.mateuszholik.domain.models.DataToSave
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test
import java.io.OutputStream

internal class SaveDataToFileUseCaseImplTest {

    private val context = mockk<Context>(relaxed = true) {
        every { contentResolver } returns CONTENT_RESOLVER
    }

    private val saveDataToFileUseCase = SaveDataToFileUseCaseImpl(context = context)

    @Test
    fun `Data is correctly saved to the file`() {
        saveDataToFileUseCase(DATA_TO_SAVE)
            .test()
            .assertComplete()

        verify(exactly = 1) { CONTENT_RESOLVER.openOutputStream(URI) }
        verify(exactly = 1) { OUTPUT_STREAM.write(DATA_TO_BE_SAVED.toByteArray()) }
        verify(exactly = 1) { OUTPUT_STREAM.close() }
    }

    private companion object {
        const val DATA_TO_BE_SAVED = "data to be saved to the file"
        val URI = mockk<Uri>()
        val OUTPUT_STREAM = mockk<OutputStream> {
            every { write(any<ByteArray>()) } returns Unit
            every { close() } returns Unit
        }
        val CONTENT_RESOLVER = mockk<ContentResolver> {
            every { openOutputStream(URI) } returns OUTPUT_STREAM
        }
        val DATA_TO_SAVE = DataToSave(
            data = DATA_TO_BE_SAVED,
            uri = URI
        )
    }
}