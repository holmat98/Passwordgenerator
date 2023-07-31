package com.mateuszholik.passwordgenerator.autofill.models

import android.view.autofill.AutofillId

data class ParsedStructure(
    val autofillId: AutofillId,
    val packageName: String?
)
