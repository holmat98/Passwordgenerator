package com.mateuszholik.domain.models

import android.net.Uri

internal data class DataToSave(
    val data: String,
    val uri: Uri?
)
