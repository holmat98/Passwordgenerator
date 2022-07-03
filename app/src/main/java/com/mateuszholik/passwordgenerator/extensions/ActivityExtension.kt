package com.mateuszholik.passwordgenerator.extensions

import androidx.annotation.IdRes
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.commit

fun FragmentActivity.removeFragment(@IdRes hostId: Int, fragmentTag: String) {
    supportFragmentManager.commit {
        supportFragmentManager.findFragmentByTag(fragmentTag)?.let {
            this.remove(it)
        }
    }
}