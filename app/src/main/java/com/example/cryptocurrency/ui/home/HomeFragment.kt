package com.example.cryptocurrency.ui.home

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cryptocurrency.R
import com.example.cryptocurrency.databinding.FragmentHomeBinding
import com.example.cryptocurrency.ui.base.BaseFragment
import com.example.cryptocurrency.utils.Resource
import com.example.cryptocurrency.utils.hide
import com.example.cryptocurrency.utils.show
import com.example.cryptocurrency.utils.showToast
import dagger.hilt.android.AndroidEntryPoint
import java.text.NumberFormat
import java.util.*

@AndroidEntryPoint
class HomeFragment : BaseFragment(R.layout.fragment_home){

    lateinit var mBinding: FragmentHomeBinding
    private val viewModel : HomeViewModel by viewModels()
    private val adapter : CryptoCurrencyAdapter by lazy {
        CryptoCurrencyAdapter(data = arrayListOf())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding = FragmentHomeBinding.bind(view)
        mBinding.recyclerView.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
        mBinding.recyclerView.adapter = adapter
        setObservers()
        viewModel.getData()
    }

    private fun setObservers() {

        mBinding.swipeRefreshView.setOnRefreshListener {
            viewModel.getData()
            mBinding.swipeRefreshView.isRefreshing = false
            mBinding.searchTv.clearFocus()
        }

        viewModel.data.observe(viewLifecycleOwner){
            when(it){
                is Resource.Error -> {
                    requireContext().showToast(it.message ?: "Error")
                    mBinding.progressBar.hide()
                }
                is Resource.Loading -> {
                    mBinding.progressBar.show()
                }
                is Resource.Success -> {
                    val res =it.data
                    mBinding.progressBar.hide()
                    adapter.update(list = res?.data ?: arrayListOf())
                    mBinding.totalAmt.text = "Global Market Cap: ${NumberFormat.getInstance(Locale.US).format(res?.data?.get(0)?.marketCapUsd?.toDouble()).dropLast(1)}"
                }
            }
        }

        mBinding.searchTv.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                adapter.filter.filter(s)
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })
    }
}