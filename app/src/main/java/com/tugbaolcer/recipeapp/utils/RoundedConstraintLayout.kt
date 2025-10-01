package com.tugbaolcer.recipeapp.utils

import android.content.Context
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout

class RoundedConstraintLayout(context: Context, attrs: AttributeSet): ConstraintLayout(context, attrs) {

    init {
        this.outlineProvider = RoundedCornersOutlineProvider(top = 32f)
        this.clipToOutline = true
    }
}