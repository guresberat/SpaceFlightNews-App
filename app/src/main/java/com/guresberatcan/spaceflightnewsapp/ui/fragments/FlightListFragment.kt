package com.guresberatcan.spaceflightnewsapp.ui.fragments

import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.guresberatcan.spaceflightnewsapp.R
import com.guresberatcan.spaceflightnewsapp.databinding.FragmentFlightListBinding
import com.guresberatcan.spaceflightnewsapp.ui.adapter.ArticleListAdapter
import com.guresberatcan.spaceflightnewsapp.ui.viewmodel.FlightListViewModel
import com.guresberatcan.spaceflightnewsapp.utils.Resource
import com.guresberatcan.spaceflightnewsapp.utils.parcelable
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FlightListFragment : Fragment() {

    private val viewModel: FlightListViewModel by viewModels()
    private val binding get() = _binding!!
    private var satelliteAdapter = ArticleListAdapter()
    private var _binding: FragmentFlightListBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFlightListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservers()
        initViews()

        if (savedInstanceState != null) {
            binding.recyclerview.layoutManager?.onRestoreInstanceState(
                savedInstanceState.parcelable("list")
            )
        }

        /*
                binding.buttonFirst.setOnClickListener {
                    findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
                }
                */

    }


    private fun initViews() {
        binding.recyclerview.apply {
            adapter = satelliteAdapter
            addItemDecoration(
                DividerItemDecoration(
                    context,
                    (layoutManager as LinearLayoutManager).orientation
                )
            )
            satelliteAdapter.itemClickListener = {
                findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
            }
        }
    }

    private fun initObservers() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                launch {
                    viewModel.articlesSharedFlow.collect {
                        when (it) {
                            is Resource.Loading -> {
                            }

                            is Resource.Success -> {
                                binding.recyclerview.apply {
                                    satelliteAdapter?.submitList(it.value)
                                }
                            }

                            is Resource.Failure -> {

                            }
                        }
                    }
                }
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable("list", binding.recyclerview.layoutManager?.onSaveInstanceState())
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}