package com.example.pooldemoapplication.config.utils

import android.content.Context
import android.content.DialogInterface.OnClickListener
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import androidx.appcompat.app.AlertDialog
import com.example.pooldemoapplication.R

class MyUtil {
    companion object {
        @JvmStatic
        fun vibrate(context: Context) {
            val vib = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                val vibratorManager =
                    context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
                vibratorManager.defaultVibrator

            } else {
                @Suppress("DEPRECATION")
                context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vib.vibrate(
                    VibrationEffect.createOneShot(
                        200,
                        VibrationEffect.DEFAULT_AMPLITUDE
                    )
                )
            } else {
                //deprecated in API 26
                @Suppress("DEPRECATION")
                vib.vibrate(200)
            }
        }

        fun alertDialog(context: Context, onClickListener: OnClickListener) {
            val builder: AlertDialog.Builder = AlertDialog.Builder(context)
            builder
                .setMessage(context.getString(R.string.are_you_sure_you_want_to_remove))
                .setPositiveButton(context.getString(R.string.delete), onClickListener)
                .setNegativeButton(context.getString(R.string.no)) { dialogInterface, i ->
                    dialogInterface.dismiss()
                }

            val dialog: AlertDialog = builder.create()
            dialog.show()
        }
    }
}