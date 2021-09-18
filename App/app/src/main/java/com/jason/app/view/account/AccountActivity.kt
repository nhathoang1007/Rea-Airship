package com.jason.app.view.account

import android.os.Handler
import android.os.Looper
import android.util.Log
import com.jason.app.R
import com.jason.app.base.adapter.BasePagerAdapter
import com.jason.app.base.view.BaseActivity
import com.jason.app.customize.view.card.CardView
import com.jason.app.databinding.ActivityMainBinding
import com.jason.app.extensions.dp
import org.koin.androidx.viewmodel.ext.android.viewModel

class AccountActivity : BaseActivity<ActivityMainBinding, AccountViewModel>(), AccountView {

    override val fragmentAdapter: BasePagerAdapter<BaseAccountFragment<*, *>> by lazy {
        BasePagerAdapter(supportFragmentManager)
    }

    override val mViewModel: AccountViewModel by viewModel()

    private var toolbarPosition = 0
    private var pagerFinishInflate: MutableList<Pair<CardState, Int>> = mutableListOf()

    override fun getLayoutRes(): Int = R.layout.activity_main

    override fun initView() {
        super.initView()
        dataBinding.apply {

            iView = this@AccountActivity

            viewPager.apply {
                initCardView(cardView)
                initPagerChangedCallback {
                    mViewModel.onStateChanged(it)
                }
                initFlyingChangedCallback { isFlying ->
                    fragmentAdapter.fragments.forEach {
                        it.onPagerFlyingChanged(isFlying)
                    }
                }
            }

            toolbar.post {
                toolbarPosition =
                    (toolbar.x + toolbar.measuredHeight).toInt() + CardView.CARD_MARGIN_TO_TOP.dp
                cardView.onAnimateVertical(toolbarPosition)
            }
        }
    }

    override fun initViewModel() {
        super.initViewModel()
        mViewModel.apply {
            fragmentsObs.observe(this@AccountActivity, {
                fragmentAdapter.addFragments(it)
                Handler(Looper.getMainLooper()).postDelayed({
                    onStateChanged(CardState.DETAILS.position)
                }, 500)
            })

            currentStateObs.observe(this@AccountActivity, {
                CardState.values().forEach { state ->
                    getFragment(state, false)?.apply {
                        if (state == it) {
                            onPageSelected()
                        } else {
                            onPageUnSelected()
                        }
                    }
                }
            })
        }
    }

    override fun navigateTo(state: CardState) {
        when {
            state == CardState.DETAILS && state == mViewModel.currentStateObs.value -> onBackPressed()
            else -> dataBinding.viewPager.currentItem = state.position
        }
    }

    override fun notifyScrollHeightChanged(height: Int, toState: CardState) {
        pagerFinishInflate.add(Pair(toState, height))
        if (pagerFinishInflate.size == CardState.values().size) {
            pagerFinishInflate.maxByOrNull { it.second }?.apply {
                Log.e("notifyScrollHeight", "$first, $second")
                getFragment(first, false)?.initScrollHeight(height)
            }
        }
    }

    override fun notifyScrolled(scrollY: Int, toState: CardState) {
        getFragment(toState)?.apply {
            onScrollTo(scrollY)
            dataBinding.cardView.onAnimateVertical(-scrollY + toolbarPosition)
        }
    }

    private fun getFragment(
        state: CardState,
        isCheckCurrentState: Boolean = true
    ): BaseAccountView<*>? {
        return if (state != mViewModel.currentStateObs.value || !isCheckCurrentState) {
            (fragmentAdapter.getItem(state.position) as BaseAccountView<*>)
        } else {
            null
        }
    }
}