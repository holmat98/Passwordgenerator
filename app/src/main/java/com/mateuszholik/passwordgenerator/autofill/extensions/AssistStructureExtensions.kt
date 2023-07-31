package com.mateuszholik.passwordgenerator.autofill.extensions

import android.app.assist.AssistStructure
import com.mateuszholik.passwordgenerator.autofill.models.ParsedStructure
import com.mateuszholik.passwordgenerator.autofill.parsers.StructureParser

fun AssistStructure.getParsedStructure(): ParsedStructure? {
    val parser = StructureParser()
    parser.parse(this)

    return parser.parsedStructure
}
