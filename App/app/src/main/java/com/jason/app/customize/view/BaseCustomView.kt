package com.jason.app.customize.view

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.annotation.StyleableRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.ViewDataBinding

/**
 * Created by Nhat.vo on 18/11/2019.
 */

abstract class BaseCustomView<V : ViewDataBinding> @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : ConstraintLayout(context, attrs, defStyle) {

    protected lateinit var binding: V

    var listener: ((Any) -> Unit)? = null

    init {
        try {
            if (attrs != null && this.getStyleable() != null) {
                val styleable = context.obtainStyledAttributes(attrs, this.getStyleable()!!, 0, 0)
                this.applyStyleable(styleable)
                styleable.recycle()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        binding = this.initViewDataBinging(LayoutInflater.from(context))
        onCreatedView()
    }

    abstract fun initViewDataBinging(inflater: LayoutInflater): V

    open fun onCreatedView() {}

    @StyleableRes
    open fun getStyleable(): IntArray? = null

    @SuppressLint("Recycle")
    open fun applyStyleable(styleable: TypedArray) {
    }

    open fun onSpecialClicked(listener: ((Any) -> Unit)?) {
        this.listener = listener
    }
}