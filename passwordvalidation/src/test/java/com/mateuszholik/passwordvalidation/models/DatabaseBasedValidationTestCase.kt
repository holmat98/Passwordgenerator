package com.mateuszholik.passwordvalidation.models

data class DatabaseBasedValidationTestCase(
    val testedValue: String,
    val firstResult: Boolean,
    val secondString: String,
    val secondResult: Boolean,
    val thirdString: String,
    val thirdResult: Boolean,
    val expected: Boolean
)
