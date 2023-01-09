package com.mateuszholik.passwordvalidation.models

data class DatabaseBasedValidationTestCase(
    val testedValue: String,
    val firstReturn: List<String>,
    val secondString: String,
    val secondReturn: List<String>,
    val thirdString: String,
    val thirdReturn: List<String>,
    val expected: Boolean
)