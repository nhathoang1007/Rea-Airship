package com.jason.app.view.account

import android.animation.ObjectAnimator
import android.util.Log
import android.view.ViewTreeObserver
import androidx.databinding.ViewDataBinding
import com.jason.app.base.view.BaseFragment
import com.jason.app.base.viewmodel.BaseViewModel

abstract class BaseAccountFragment<BD : ViewDataBinding, VM : BaseViewModel> :
    BaseFragment<BD, VM>(), BaseAccountView<VM> {

    private var currentScrollY = 0

    override fun initView() {
        super.initView()
        onPageSelected()
        getContainerSpacingView().apply {
            post {
                layoutParams.height = rootView.width
                requestLayout()
            }
        }
    }

    override fun onPageSelected() {
        getScroller().apply {
            setOnScrollChanged { _, y ->
                if (y == currentScrollY) return@setOnScrollChanged
                currentScrollY = y
                getParentView().notifyScrolled(y, notifyToState)
            }
            if (scrollY > 0) {
                onScrollTo(0)
            }
        }
    }

    override fun onPageUnSelected() {
        getScroller().setOnScrollChanged(null)
    }

    override fun onPagerFlyingChanged(isFlying: Boolean) {
        getScroller().setBlockedScroller(isFlying)
    }

    protected fun onDetectScrollHeight() {
        getListView().apply {
            post {
                postDelayed({
                    getParentView().notifyScrollHeightChanged(getContainer().measuredHeight, notifyToState)
                }, 1000)
            }
        }
    }

    override fun initScrollHeight(height: Int) {
        getContainer().apply {
            post {
                when {
                    height > measuredHeight -> {
                        getAddingSpace().let {
                            Log.e("getAddingSpace", "$height, $measuredHeight")
                            it.layoutParams.height = height - measuredHeight
                            it.requestLayout()
                        }
                    }
                    else -> {
                        return@post
                    }
                }
            }
        }
    }

    override fun onScrollTo(y: Int) {
        getScroller().apply {
            if (y == 0) {
                ObjectAnimator.ofInt(this, "scrollY", y).setDuration(300).start()
            } else {
                smoothScrollTo(scrollX, y)
            }
        }
    }

    override fun getParentView(): AccountView {
        return super.getParentView() as AccountView
    }
}