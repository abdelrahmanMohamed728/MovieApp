package com.example.basemodule.basemodule.utils

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText


class KeyboardUtils {
    fun hideSoftKeyboard(view: View?) {
        if (view == null) {
            return
        }
        val context = view.context
        val inputMethodManager =
            context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(
            view.windowToken,
            InputMethodManager.RESULT_UNCHANGED_SHOWN
        )
    }

    fun showSoftKeyboard(view: View?){
        val context = view?.context

        val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
        imm?.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
    }

    fun moveCursorTo(et: EditText, position: Int){
        et.setSelection(position)
    }
}