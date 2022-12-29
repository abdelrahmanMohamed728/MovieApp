package com.example.drdbasemodule.utils

import android.view.View

/**
 * Created by Shaza Hassan on 3/28/21
 */

fun View.visible(isVisible: Boolean) {
    visibility = if (isVisible) View.VISIBLE else View.GONE
}