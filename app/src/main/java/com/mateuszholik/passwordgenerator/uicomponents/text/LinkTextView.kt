package com.mateuszholik.passwordgenerator.uicomponents.text

import android.content.Context
import android.text.util.Linkify
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView

class LinkTextView @JvmOverloads constructor(
    context: Context,
    attrRes: AttributeSet? = null,
) : AppCompatTextView(context, attrRes) {

    var urlLink: String = ""
        set(value) {
            text = value
            field = value
            Linkify.addLinks(this, Linkify.WEB_URLS)
        }
}
