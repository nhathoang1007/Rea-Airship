package com.jason.app.customize.view.card

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import com.jason.app.customize.view.BaseCustomView
import com.jason.app.databinding.CustomCardViewBinding
import com.jason.app.extensions.dp
import com.jason.app.utils.BindingUtils.isVisible
import kotlin.math.min


class CardView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : BaseCustomView<CustomCardViewBinding>(context, attrs, defStyle) {

    private var originalX = 0.0f
    private var minX: Float = 0.0f
    private val padding = 24.dp
    private var showLeftWidth = 0.0f

    init {
        isClickable = false
        isFocusable = false
        isEnabled = false
    }

    override fun initViewDataBinging(inflater: LayoutInflater): CustomCardViewBinding {
        return CustomCardViewBinding.inflate(inflater, this, true)
    }

    override fun onCreatedView() {
        super.onCreatedView()
        post {
            val mWidth = ((rootView.width - padding * 2) * FULL_WIDTH_RATIO_SCALE).toInt()
            val mHeight = rootView.width
            layoutParams.apply {
                width = mWidth
                height = mHeight
                requestLayout()
            }

            binding.cardContainer.apply {
                layoutParams.apply {
                    width = mHeight
                    height = mWidth
                    requestLayout()
                }

            }

            binding.breakLine.apply {
                layoutParams.apply {
                    height = mWidth * 1 / 5
                    requestLayout()
                }
            }

            showLeftWidth = width * LEFT_RATIO_SCALE

            x = rootView.width - showLeftWidth

            originalX = rootView.width - showLeftWidth
            minX = showLeftWidth + padding.toFloat()
            requestLayout()

            isVisible(true)
        }
    }

    fun onAnimateHorizontal(position: Int) {
        if (position == 0) return

        animate()
            .x(min(originalX, calculate(position)))
            .setDuration(0)
            .start()
    }

    fun onAnimateVertical(position: Int) {
        animate()
            .y(position.toFloat())
            .setDuration(0)
            .start()
    }

    private fun calculate(position: Int): Float {
        val scale = position.toFloat() / rootView.width.toFloat() * minX
        return rootView.width - showLeftWidth - position.toFloat() + scale
    }

    companion object {
        const val CARD_MARGIN_TO_TOP = 32
        private const val LEFT_RATIO_SCALE: Float = 1.toFloat() / 7.toFloat()
        private const val FULL_WIDTH_RATIO_SCALE: Float = 6.toFloat() / 10.toFloat()
    }
}