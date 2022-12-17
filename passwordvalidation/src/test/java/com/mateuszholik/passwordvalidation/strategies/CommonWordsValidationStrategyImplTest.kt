package com.mateuszholik.passwordvalidation.strategies

import com.mateuszholik.passwordvalidation.db.daos.CommonWordDao
import com.mateuszholik.passwordvalidation.transformers.StringTransformer
import io.mockk.mockk
import org.junit.jupiter.api.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4


class CommonWordsValidationStrategyImplTest {

    private val commonWordDao = mockk<CommonWordDao>()
    private val stringTransformer = mockk<StringTransformer>()
    private val commonWordsValidationStrategy = CommonWordsValidationStrategyImpl(
        commonWordDao = commonWordDao,
        stringTransformer = stringTransformer
    )

    @Test
    fun `When there is no matching words in the password result is true and score is equal to maxScore`() {

    }

    private companion object {

    }
}