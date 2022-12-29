package com.example.drdbasemodule.loading

import android.app.Activity
import android.app.Dialog
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.Color.GREEN
import android.graphics.drawable.ColorDrawable
import android.view.Window
import android.widget.ProgressBar
import androidx.core.graphics.BlendModeColorFilterCompat
import androidx.core.graphics.BlendModeCompat
import com.example.basemodule.R

class LoadingUtil {

    companion object {
        fun showProgress(activity: Activity): Dialog {
            val dialog = Dialog(activity)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.window?.setBackgroundDrawable(
                    ColorDrawable(0)
            )
            dialog.setContentView(R.layout.dialog_progress)
            val progressBar: ProgressBar = dialog.findViewById(R.id.progressBar)
            progressBar.indeterminateDrawable.colorFilter = BlendModeColorFilterCompat.createBlendModeColorFilterCompat(
                    progressBar.context.getColor(R.color.day_night_green), BlendModeCompat.SRC_ATOP
            )
            dialog.setCancelable(false)
            dialog.show()
            return dialog
        }
    }
}