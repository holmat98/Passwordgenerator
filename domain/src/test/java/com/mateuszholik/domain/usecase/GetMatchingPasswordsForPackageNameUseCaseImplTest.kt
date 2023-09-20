package com.mateuszholik.domain.usecase

import com.mateuszholik.data.repositories.PasswordsRepository
import com.mateuszholik.data.repositories.models.AutofillPasswordDetails
import io.mockk.every
import io.mockk.mockk
import io.reactivex.rxjava3.core.Single
import org.junit.jupiter.api.Test

internal class GetMatchingPasswordsForPackageNameUseCaseImplTest {

    private val passwordsRepository = mockk<PasswordsRepository> {
        every {
            getAutofillPasswordsDetails()
        } returns Single.just(
            listOf(
                AUTOFILL_PASSWORDS_DETAILS,
                AUTOFILL_PASSWORDS_DETAILS_2,
                AUTOFILL_PASSWORDS_DETAILS_3,
                AUTOFILL_PASSWORDS_DETAILS_4,
            )
        )
    }

    private val getMatchingPasswordsForPackageNameUseCase = GetMatchingPasswordsForPackageNameUseCaseImpl(
        passwordsRepository = passwordsRepository
    )

    @Test
    fun `When the searched package name was found in the list of passwords then the list of matching passwords is returned`() {
        getMatchingPasswordsForPackageNameUseCase(MATCHING_PACKAGE_NAME)
            .test()
            .assertComplete()
            .assertValue(listOf(AUTOFILL_PASSWORDS_DETAILS_2))
    }

    @Test
    fun `When the searched package name is null then empty list is returned`() {
        getMatchingPasswordsForPackageNameUseCase(null)
            .test()
            .assertComplete()
            .assertValue(emptyList())
    }

    @Test
    fun `When the searched package name was not found in the list of passwords then empty list is returned`() {
        getMatchingPasswordsForPackageNameUseCase(NOT_MATCHING_PACKAGE_NAME)
            .test()
            .assertComplete()
            .assertValue(emptyList())
    }

    @Test
    fun `When error occurred then empty list is returned`() {
        every {
            passwordsRepository.getAutofillPasswordsDetails()
        } returns Single.error(Exception("Something went wrong"))

        getMatchingPasswordsForPackageNameUseCase(MATCHING_PACKAGE_NAME)
            .test()
            .assertComplete()
            .assertValue(emptyList())
    }

    private companion object {
        const val MATCHING_PACKAGE_NAME = "com.example.packagename"
        const val NOT_MATCHING_PACKAGE_NAME = "com.example.packagename3"
        val AUTOFILL_PASSWORDS_DETAILS = AutofillPasswordDetails(
            id = 1,
            platformName = "platform",
            password = "password",
            packageName = null
        )
        val AUTOFILL_PASSWORDS_DETAILS_2 = AutofillPasswordDetails(
            id = 2,
            platformName = "platform2",
            password = "password2",
            packageName = "com.example.packagename"
        )
        val AUTOFILL_PASSWORDS_DETAILS_3 = AutofillPasswordDetails(
            id = 3,
            platformName = "platform3",
            password = "password3",
            packageName = null
        )
        val AUTOFILL_PASSWORDS_DETAILS_4 = AutofillPasswordDetails(
            id = 4,
            platformName = "platform4",
            password = "password4",
            packageName = "com.example.packagename2"
        )
    }
}
