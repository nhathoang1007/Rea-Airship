package com.jason.app.view.account.details

import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import com.jason.app.databinding.FragmentCardDetailsBinding
import com.jason.app.view.account.BaseAccountFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class CardDetailsFragment : BaseAccountFragment<FragmentCardDetailsBinding, CardDetailsViewModel>(),
    CardDetailsView {

    override val chartAdapter: MoneyChartAdapter by lazy {
        MoneyChartAdapter {
            mViewModel.onFocusChartChanged(it)
        }
    }

    override val mViewModel: CardDetailsViewModel by viewModel()

    override fun getViewBinding(
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentCardDetailsBinding.inflate(layoutInflater)

    override fun initView() {
        super.initView()
        dataBinding.apply{
            iView = this@CardDetailsFragment
        }
    }

    override fun initViewModel() {
        super.initViewModel()
        mViewModel.apply {
            chartObs.observe(viewLifecycleOwner, {
                chartAdapter.updateData(it.first, it.second)
                onDetectScrollHeight()
            })
        }
    }

    override fun getContainerSpacingView() = dataBinding.containerSpace

    override fun getScroller() = dataBinding.scrollView

    override fun getAddingSpace() = dataBinding.addingSpace

    override fun getContainer() = dataBinding.container

    override fun getListView() = dataBinding.rvChart
}