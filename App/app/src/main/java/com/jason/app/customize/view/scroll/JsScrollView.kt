package com.jason.app.customize.view.scroll

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.core.widget.NestedScrollView

class JsScrollView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : NestedScrollView(context, attrs, defStyle) {

    private var isScrolledToTop: Boolean = false
    private var isBlockedScroller: Boolean = false
    private var onScrollChanged: ((Boolean, Int) -> Unit)? = null

    override fun onScrollChanged(l: Int, t: Int, oldl: Int, oldt: Int) {
        if (t > oldt) {
            isScrolledToTop = false
            onScrollChanged?.invoke(true, t)
        } else if (t < oldt && !isScrolledToTop) {
            if (t <= 0) {
                isScrolledToTop = true
            }
            onScrollChanged?.invoke(false, t)
        }

        super.onScrollChanged(l, t, oldl, oldt)
    }

    fun setOnScrollChanged(onScrollChanged: ((Boolean, Int) -> Unit)? = null) {
        this.onScrollChanged = onScrollChanged
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        return !isBlockedScroller && super.onInterceptTouchEvent(ev)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(ev: MotionEvent?): Boolean {
        return !isBlockedScroller && super.onTouchEvent(ev)
    }

    fun setBlockedScroller(isBlocked: Boolean) {
        this.isBlockedScroller = isBlocked
    }
}