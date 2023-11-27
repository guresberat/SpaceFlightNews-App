package com.guresberatcan.spaceflightnewsapp.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.guresberatcan.spaceflightnewsapp.R
import com.guresberatcan.spaceflightnewsapp.databinding.FragmentFlightListBinding
import com.guresberatcan.spaceflightnewsapp.ui.adapter.ArticleListAdapter
import com.guresberatcan.spaceflightnewsapp.ui.viewmodel.FlightListViewModel
import com.guresberatcan.data.util.parcelable
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.Timer
import java.util.TimerTask


@AndroidEntryPoint
class FlightListFragment : Fragment(R.layout.fragment_flight_list) {

    private val viewModel: FlightListViewModel by viewModels()
    private var _binding: FragmentFlightListBinding? = null
    private val binding : FragmentFlightListBinding get() = _binding!!
    private var articleAdapter = ArticleListAdapter()
    private lateinit var timer : Timer

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentFlightListBinding.bind(view)
        initObservers()
        initViews()
        initializeTimer()
        if (savedInstanceState != null) {
            binding.recyclerview.layoutManager?.onRestoreInstanceState(
                savedInstanceState.parcelable("list")
            )
        }
    }

    private fun initializeTimer() {
        timer = Timer()
        val task = object : TimerTask() {
            override fun run() {
                viewModel.getArticles()
            }
        }
        timer.scheduleAtFixedRate(task, 1, 60000)
    }

    private fun initViews() {
        binding.recyclerview.apply {
            adapter = articleAdapter
            articleAdapter.itemClickListener = {
                val bundle = Bundle().apply {
                    it.id?.let { it1 -> putInt("id", it1) }
                }
                findNavController().navigate(
                    R.id.action_FirstFragment_to_SecondFragment,
                    bundle
                )
            }
            articleAdapter.favouriteClickedListener = {
                it.isFavourite = it.isFavourite.not()
                viewModel.updateArticle(it.id, it.isFavourite)
                articleAdapter.notifyItemChanged(articleAdapter.currentList.indexOf(it))
            }
        }
        binding.searchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    viewModel.filter(it)
                }
                return false
            }
        })
    }

    private fun initObservers() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                launch {
                    viewModel.articlesSharedFlow.collect {
                        when (it) {
                            is com.guresberatcan.domain.utils.Resource.Success -> {
                                binding.recyclerview.apply {
                                    articleAdapter.submitList(it.data)
                                }
                            }

                            is com.guresberatcan.domain.utils.Resource.Error -> {
                                //Handle error
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
        timer.cancel()
    }
}