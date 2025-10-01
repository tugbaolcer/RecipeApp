package com.tugbaolcer.recipeapp.utils

import android.content.Context
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.tugbaolcer.recipeapp.R
import com.tugbaolcer.recipeapp.databinding.LayoutCustomTextInputBinding

class CustomTextInputLayout(context: Context, attrs: AttributeSet) :
    ConstraintLayout(context, attrs) {

    var binding = LayoutCustomTextInputBinding.inflate(LayoutInflater.from(context), this, true)

    val inputText: String
        get() = binding.etCustom.text.toString()

    fun setText(text: String) {
        this.binding.etCustom.setText(text)
    }

    private var isPasswordVisible = false

    init {
        binding.apply {
            context.theme.obtainStyledAttributes(attrs, R.styleable.CustomTextInputLayout, 0, 0)
                .let { typedArray ->
                    try {

                        val hintString =
                            typedArray.getString(R.styleable.CustomTextInputLayout_til_hint)
                        tilCustom.hint = hintString

                        val isShowButton =
                            typedArray.getBoolean(
                                R.styleable.CustomTextInputLayout_til_show_password,
                                false
                            )

                        if (isShowButton){
                            updatePasswordVisibility()
                            showPasswordButton.visibility = VISIBLE
                        } else {
                            showPasswordButton.visibility = GONE

                        }

                    } finally {
                        root.invalidate()
                        typedArray.recycle()
                    }
                }

            etCustom.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {}
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(
                    editable: CharSequence?,
                    start: Int,
                    before: Int,
                    count: Int
                ) {
                    etCustom.post {
                        val editableText = editable.toString()
                        etCustom.let {
                            it.removeTextChangedListener(this)
                            it.setText(editableText)
                            it.setSelection(start + count)
                            it.addTextChangedListener(this)
                        }

                    }
                }
            })

            setupButtonClickListener()
        }
    }

    private fun setupButtonClickListener() {
        binding.showPasswordButton.setOnClickListener {
            isPasswordVisible = !isPasswordVisible
            updatePasswordVisibility()
        }
    }

    private fun updatePasswordVisibility() {
        binding.apply {
            if (isPasswordVisible) {
                etCustom.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                showPasswordButton.text = context.getString(R.string.Common_Label_Gone)
            } else {
                etCustom.inputType =
                    InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                showPasswordButton.text = context.getString(R.string.Common_Label_Show)
            }
            etCustom.setSelection(etCustom.text?.length ?: 0)
        }
    }
}