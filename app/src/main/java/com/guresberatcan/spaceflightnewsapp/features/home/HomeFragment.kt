package com.guresberatcan.spaceflightnewsapp.features.home

import android.os.Bundle
import android.os.Parcelable
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.guresberatcan.data.util.parcelable
import com.guresberatcan.domain.utils.Constants.BUNDLE_ARTICLE_ID
import com.guresberatcan.domain.utils.Constants.RECYCLERVIEW_INSTANCE_STATE
import com.guresberatcan.spaceflightnewsapp.R
import com.guresberatcan.spaceflightnewsapp.databinding.FragmentHomeBinding
import com.guresberatcan.spaceflightnewsapp.features.home.adapter.ArticleListAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.Timer
import java.util.TimerTask


@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    private val viewModel: HomeViewModel by viewModels()
    private var _binding: FragmentHomeBinding? = null
    private val binding: FragmentHomeBinding get() = _binding!!

    private var articleAdapter = ArticleListAdapter()

    private lateinit var timer: Timer
    private var isFirstOpen = true

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentHomeBinding.bind(view)
        initObservers()
        initViews()
    }

    override fun onResume() {
        super.onResume()
        initializeTimer()
    }

    private fun initializeTimer() {
        timer = Timer()
        val task = object : TimerTask() {
            override fun run() {
                viewModel.getArticles()
            }
        }
        timer.scheduleAtFixedRate(task, FETCH_DELAY_MILLIS, FETCH_PERIOD_MILLIS)
    }

    private fun initViews() {
        binding.recyclerview.apply {
            adapter = articleAdapter
            articleAdapter.itemClickListener = { article ->
                Bundle().apply {
                    article.id?.let { putInt(BUNDLE_ARTICLE_ID, it) }
                    putParcelable(
                        RECYCLERVIEW_INSTANCE_STATE,
                        binding.recyclerview.layoutManager?.onSaveInstanceState()
                    )
                }.also {
                    findNavController().navigate(
                        R.id.action_FirstFragment_to_SecondFragment,
                        it
                    )
                }
            }
            articleAdapter.favouriteClickedListener = { article, position ->
                article.isFavourite = article.isFavourite.not()
                viewModel.updateArticle(article.id, article.isFavourite)
                articleAdapter.notifyItemChanged(position)
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

    private fun scrollToPosition() {
        if (isFirstOpen) {
            try {
                val positionData =
                    requireArguments().parcelable<Parcelable>(RECYCLERVIEW_INSTANCE_STATE)
                binding.recyclerview.layoutManager?.onRestoreInstanceState(
                    positionData
                )
                isFirstOpen = false
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun initObservers() {
        lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.articlesSharedFlow.collect {
                        when (it) {
                            is com.guresberatcan.domain.utils.Resource.Success -> {
                                it.data?.let { it1 -> articleAdapter.submitList(it1) }
                                scrollToPosition()
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        timer.cancel()
    }

    companion object {
        const val FETCH_PERIOD_MILLIS = 60000L
        const val FETCH_DELAY_MILLIS = 1L
    }
}