package com.mateuszholik.data.repositories.models

data class AutofillPasswordDetails(
    val id: Long,
    val platformName: String,
    val password: String,
    val packageName: String?,
)
