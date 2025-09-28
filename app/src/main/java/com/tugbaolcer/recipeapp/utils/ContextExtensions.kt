package com.tugbaolcer.recipeapp.utils

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.tugbaolcer.recipeapp.App.Companion.mAlertDialog
import com.tugbaolcer.recipeapp.R
import com.tugbaolcer.recipeapp.databinding.LayoutAlertMessageBinding
import androidx.core.graphics.drawable.toDrawable


fun Context.showSuccessAlert(
    message: String
) {
    showBaseAlert(ALERT_SUCCESS, message)
}

fun Context.showSuccessAlert(
    messageRes: Int
) {
    showBaseAlert(ALERT_SUCCESS, getString(messageRes))
}

fun Context.showInfoAlert(
    messageRes: Int
) {
    showBaseAlert(ALERT_INFO, getString(messageRes))
}

fun Context.showErrorAlert(
    message: String
) {
    showBaseAlert(ALERT_ERROR, message)
}

fun Context.showErrorAlert(
    messageRes: Int
) {
    showBaseAlert(ALERT_ERROR, getString(messageRes))
}



fun Context.showBaseAlert(
    alertType: Int,
    message: String
) {
    try {

        val binding = LayoutAlertMessageBinding.inflate(LayoutInflater.from(this))

        AlertDialog.Builder(this).setView(binding.root).show().let { alertDialog ->

            if (mAlertDialog != null && mAlertDialog!!.isShowing)
                try {
                    mAlertDialog!!.dismiss()
                } catch (e: Exception) {
                    Log.e("DIALOG_ERROR", e.localizedMessage?.toString() ?: "DIALOG_ERROR")
                }

            mAlertDialog = alertDialog
            alertDialog.window?.apply {
                attributes.verticalMargin = 0.1F
                setGravity(Gravity.TOP)
                setBackgroundDrawable(Color.TRANSPARENT.toDrawable())
                setDimAmount(0.1f)
            }

            alertDialog.apply {
                binding.apply {
                    when (alertType) {
                        ALERT_ERROR -> {
                            cardView.strokeColor = ContextCompat.getColor(context, R.color.brand_red)
                            ivAlertIcon.setImageResource(R.drawable.bg_alert_error)
                            container.backgroundTintList =
                                ColorStateList.valueOf(ContextCompat.getColor(context, R.color.light_information))
                            ibCancel.backgroundTintList =
                                ColorStateList.valueOf(ContextCompat.getColor(context, R.color.light_information))
                        }

                        ALERT_SUCCESS -> {
                            cardView.strokeColor = ContextCompat.getColor(context, R.color.brand_green)
                            ivAlertIcon.setImageResource(R.drawable.bg_alert_success)
                            container.backgroundTintList =
                                ColorStateList.valueOf(ContextCompat.getColor(context, R.color.light_error))
                            ibCancel.backgroundTintList =
                                ColorStateList.valueOf(ContextCompat.getColor(context, R.color.light_error))
                        }

                        ALERT_INFO -> {
                            cardView.strokeColor = ContextCompat.getColor(context, R.color.brand_blue)
                            ivAlertIcon.setImageResource(R.drawable.bg_alert_info)
                            container.backgroundTintList =
                                ColorStateList.valueOf(ContextCompat.getColor(context, R.color.light_information))
                            ibCancel.backgroundTintList =
                                ColorStateList.valueOf(ContextCompat.getColor(context, R.color.light_information))
                        }
                    }

                    tvMessage.text = message
                    ibCancel.setOnClickListener {
                        dismiss()
                    }
                    show()
                }
            }
        }
    } catch (e: Exception) {
        FirebaseCrashlytics.getInstance().recordException(e)
    }
}



