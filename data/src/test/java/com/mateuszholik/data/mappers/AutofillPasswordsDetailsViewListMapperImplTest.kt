package com.mateuszholik.data.mappers

import com.mateuszholik.data.db.models.views.AutofillPasswordDetailsView
import com.mateuszholik.data.repositories.models.AutofillPasswordDetails
import io.mockk.every
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class AutofillPasswordsDetailsViewListMapperImplTest {

    private val autofillPasswordDetailsViewMapper = mockk<AutofillPasswordDetailsViewMapper>()

    private val mapper = AutofillPasswordsDetailsViewListMapperImpl(
        autofillPasswordDetailsViewMapper = autofillPasswordDetailsViewMapper
    )

    @Test
    fun `The list of the AutofillPasswordDetailsView objects is correctly mapped to the list of AutofillPasswordDetails objects`() {
        every {
            autofillPasswordDetailsViewMapper.map(AUTOFILL_PASSWORD_DETAILS_VIEW)
        } returns AUTOFILL_PASSWORD_DETAILS

        val result = mapper.map(listOf(AUTOFILL_PASSWORD_DETAILS_VIEW))

        assertThat(result).isEqualTo(listOf(AUTOFILL_PASSWORD_DETAILS))
    }

    private companion object {
        const val PLATFORM_NAME = "platform"
        const val PASSWORD = "password"
        const val PACKAGE_NAME = "com.example.package"
        val AUTOFILL_PASSWORD_DETAILS_VIEW = AutofillPasswordDetailsView(
            id = 1,
            platformName = ByteArray(5) { it.toByte() },
            platformNameIv = ByteArray(6) { it.toByte() },
            password = ByteArray(7) { it.toByte() },
            passwordIv = ByteArray(8) { it.toByte() },
            packageName = ByteArray(9) { it.toByte() },
            packageNameIv = ByteArray(10) { it.toByte() }
        )
        val AUTOFILL_PASSWORD_DETAILS = AutofillPasswordDetails(
            id = 1,
            platformName = PLATFORM_NAME,
            password = PASSWORD,
            packageName = PACKAGE_NAME
        )
    }
}
