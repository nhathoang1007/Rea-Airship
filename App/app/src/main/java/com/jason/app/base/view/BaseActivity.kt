package com.jason.app.base.view

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import com.jason.app.base.viewmodel.BaseViewModel
import com.jason.app.MyApp
import com.jason.app.customize.dialog.LoadingDialog
import com.jason.app.extensions.applicationContext
import com.jason.app.extensions.getDefault
import com.jason.app.extensions.logError
import org.koin.core.context.KoinContextHandler

abstract class BaseActivity<V : ViewDataBinding, VM : BaseViewModel> : AppCompatActivity(), BaseView<VM> {

    protected val TAG = this::class.simpleName

    protected lateinit var dataBinding: V

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.lifecycle.addObserver(mViewModel)
        dataBinding = DataBindingUtil.setContentView(this, getLayoutRes())
        dataBinding.lifecycleOwner = this
        initView()
        initViewModel()
    }

    @LayoutRes
    abstract fun getLayoutRes(): Int

    open fun initView() {}

    open fun initViewModel() {
        mViewModel.apply {
            isLoadingObs.observe(this@BaseActivity, {
                handleLoading(it)
            })
            errorObs.observe(this@BaseActivity, {
                handleError(it)
            })
        }
    }

    override fun handleError(error: Throwable?) {
        error?.message?.let {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }
    }

    override fun handleLoading(isLoading: Boolean?) {
        if (isLoading.getDefault()) {
            LoadingDialog.show(this)
        } else {
            LoadingDialog.dismiss()
        }
    }

    override val mContext: Context = applicationContext()
}