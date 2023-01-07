package com.mateuszholik.domain.usecase

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test
import java.io.ByteArrayInputStream

internal class ReadDataFromFileUseCaseImplTest {

    private val context = mockk<Context>(relaxed = true) {
        every { contentResolver } returns CONTENT_RESOLVER
    }

    private val readDataFromFileUseCase = ReadDataFromFileUseCaseImpl(context = context)

    @Test
    fun `Data is correctly read from the file`() {
        readDataFromFileUseCase(URI)
            .test()
            .assertValue(DATA_FROM_FILE)

        verify(exactly = 1) { CONTENT_RESOLVER.openInputStream(URI) }
    }

    private companion object {
        const val DATA_FROM_FILE = "data to be saved to the file"
        val DATA_FROM_FILE_AS_BYTE_ARRAY = DATA_FROM_FILE.toByteArray()
        val URI = mockk<Uri>()
        val INPUT_STREAM = ByteArrayInputStream(DATA_FROM_FILE_AS_BYTE_ARRAY)
        val CONTENT_RESOLVER = mockk<ContentResolver> {
            every { openInputStream(URI) } returns INPUT_STREAM
        }
    }
}