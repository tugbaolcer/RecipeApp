package com.tugbaolcer.recipeapp.utils

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.PorterDuff
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.tugbaolcer.recipeapp.R
import androidx.core.graphics.drawable.toDrawable


class ProgressDialog(context: Context) : Dialog(context), View.OnClickListener {

    var textView: AppCompatTextView

    override fun onClick(p0: View?) {}

    init {
        setContentView(R.layout.layout_dialog_progress)
        val constraintLayout = findViewById<ConstraintLayout>(R.id.dialogWrapper)
        val progressBar = findViewById<ProgressBar>(R.id.progress)
        textView = findViewById<AppCompatTextView>(R.id.tv_text)

        progressBar.background = Color.TRANSPARENT.toDrawable()
        progressBar.isIndeterminate = true
        progressBar.indeterminateDrawable.setColorFilter(
            -0x1,
            PorterDuff.Mode.MULTIPLY
        )
        if (window != null) {
            window!!.setBackgroundDrawable(Color.TRANSPARENT.toDrawable())
        }
        constraintLayout.setOnClickListener(this)
        progressBar.setOnClickListener(this)
        setCanceledOnTouchOutside(false)
        setCancelable(false)
    }

    fun showText(isTextVisible: Boolean) {
        if (isTextVisible)
            textView.visibility = View.VISIBLE
        else
            textView.visibility = View.GONE
    }

}
