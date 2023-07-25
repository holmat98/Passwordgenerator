package com.mateuszholik.passwordgenerator.autofill.parsers

import android.app.assist.AssistStructure
import android.view.autofill.AutofillId

class StructureParser(private val autofillStructure: AssistStructure) {

    val autoFillIds = ArrayList<AutofillId>()

    fun parse() {
        val nodes = autofillStructure.windowNodeCount

        for (index in 0 until nodes) {
            val node = autofillStructure.getWindowNodeAt(index).rootViewNode

            searchForPasswordEditText(node)
        }
    }

    private fun searchForPasswordEditText(node: AssistStructure.ViewNode) {
        val numOfChildren = node.childCount

        for (childIndex in 0 until numOfChildren) {
            val child = node.getChildAt(childIndex)
            val id = child.autofillId

            when {
                child.autofillHints?.contains(PASSWORD_FIELD_TEXT) == true ||
                        child.isPasswordEditText() ->
                    id?.let { autoFillIds.add(it) }
                else -> searchForPasswordEditText(child)
            }
        }
    }

    private fun AssistStructure.ViewNode.isPasswordEditText(): Boolean =
        className == EDIT_TEXT_CLASS_NAME && (
                hint?.contains(PASSWORD_FIELD_TEXT, true) == true ||
                        text?.contains(PASSWORD_FIELD_TEXT, true) == true
                )

    private companion object {
        const val EDIT_TEXT_CLASS_NAME = "android.widget.EditText"
        const val PASSWORD_FIELD_TEXT = "password"
    }
}
