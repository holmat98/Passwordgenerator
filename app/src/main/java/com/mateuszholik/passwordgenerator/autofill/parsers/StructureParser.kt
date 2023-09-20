package com.mateuszholik.passwordgenerator.autofill.parsers

import android.app.assist.AssistStructure
import android.text.InputType
import com.mateuszholik.passwordgenerator.autofill.models.ParsedStructure

class StructureParser {

    fun parse(assistStructure: AssistStructure): ParsedStructure? {

        val nodes = assistStructure.windowNodeCount

        for (index in 0 until nodes) {
            val node = assistStructure.getWindowNodeAt(index).rootViewNode
            val result = searchForPasswordEditText(node)

            if (result != null) {
                return result
            }
        }

        return null
    }

    private fun searchForPasswordEditText(node: AssistStructure.ViewNode): ParsedStructure? {
        val numOfChildren = node.childCount

        for (childIndex in 0 until numOfChildren) {
            val child = node.getChildAt(childIndex)

            val isPasswordEditText = child.autofillHints?.contains(PASSWORD_FIELD_TEXT) == true ||
                    child.isPasswordInputType() || child.isPasswordEditText()

            if (isPasswordEditText) {
                return child.autofillId?.let { autofillId ->
                    ParsedStructure(
                        autofillId = autofillId,
                        packageName = child.idPackage
                    )
                }
            }

            val resultForChildren = searchForPasswordEditText(child)

            if (resultForChildren != null) {
                return resultForChildren
            }
        }

        return null
    }

    private fun AssistStructure.ViewNode.isPasswordEditText(): Boolean =
        className == EDIT_TEXT_CLASS_NAME && (
                hint?.contains(PASSWORD_FIELD_TEXT, true) == true ||
                        text?.contains(PASSWORD_FIELD_TEXT, true) == true
                )

    private fun AssistStructure.ViewNode.isPasswordInputType(): Boolean =
        when (inputType) {
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
