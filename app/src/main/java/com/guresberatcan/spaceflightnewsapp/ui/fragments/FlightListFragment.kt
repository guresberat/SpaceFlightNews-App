package com.guresberatcan.spaceflightnewsapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import com.guresberatcan.spaceflightnewsapp.utils.Resource
import com.guresberatcan.spaceflightnewsapp.utils.parcelable
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.Timer
import java.util.TimerTask


@AndroidEntryPoint
class FlightListFragment : Fragment() {

    private val viewModel: FlightListViewModel by viewModels()
    private val binding get() = _binding!!
    private var articleAdapter = ArticleListAdapter()
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
        initializeTimer()
        if (savedInstanceState != null) {
            binding.recyclerview.layoutManager?.onRestoreInstanceState(
                savedInstanceState.parcelable("list")
            )
        }
    }

    private fun initializeTimer() {
        val timer = Timer()
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
                findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
                /* Favoruite button is not implemented yet
                it.isFavourite = it.isFavourite.not()
                viewModel.updateArticle(it.id, it.isFavourite)
                */
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
                            is Resource.Success -> {
                                binding.recyclerview.apply {
                                    articleAdapter.submitList(it.data)
                                }
                            }

                            is Resource.Error -> {
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
    }
}