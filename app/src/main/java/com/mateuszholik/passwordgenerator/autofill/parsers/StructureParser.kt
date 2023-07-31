package com.mateuszholik.passwordgenerator.autofill.parsers

import android.app.assist.AssistStructure
import android.text.InputType
import com.mateuszholik.passwordgenerator.autofill.models.ParsedStructure

class StructureParser {

    private var packageName: String? = null

    var parsedStructure: ParsedStructure? = null

    fun parse(assistStructure: AssistStructure) {
        val nodes = assistStructure.windowNodeCount

        for (index in 0 until nodes) {
            val node = assistStructure.getWindowNodeAt(index).rootViewNode
            searchForPasswordEditText(node)
        }
    }

    private fun searchForPasswordEditText(node: AssistStructure.ViewNode) {
        val numOfChildren = node.childCount

        for (childIndex in 0 until numOfChildren) {
            val child = node.getChildAt(childIndex)

            when {
                child.autofillHints?.contains(PASSWORD_FIELD_TEXT) == true ||
                        child.isPasswordEditText() ||
                        child.isPasswordInputType() -> child.autofillId?.let {
                    parsedStructure = ParsedStructure(
                        autofillId = it,
                        packageName = packageName ?: child.idPackage
                    )
                }
                else -> {
                    packageName = child.idPackage
                    searchForPasswordEditText(child)
                }
            }
        }
    }

    private fun AssistStructure.ViewNode.isPasswordEditText(): Boolean =
        className == EDIT_TEXT_CLASS_NAME && (
                hint?.contains(PASSWORD_FIELD_TEXT, true) == true ||
                        text?.contains(PASSWORD_FIELD_TEXT, true) == true
                )

    private fun AssistStructure.ViewNode.isPasswordInputType(): Boolean =
        when (this.inputType) {
            InputType.TYPE_NUMBER_VARIATION_PASSWORD,
            InputType.TYPE_TEXT_VARIATION_PASSWORD,
            InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD,
            InputType.TYPE_TEXT_VARIATION_WEB_PASSWORD,
            PASSWORD_OR_CLASS_TEXT_INPUT_TYPE -> true
            else -> false
        }

    private companion object {
        const val PASSWORD_OR_CLASS_TEXT_INPUT_TYPE = 129
        const val EDIT_TEXT_CLASS_NAME = "android.widget.EditText"
        const val PASSWORD_FIELD_TEXT = "password"
    }
}
