package com.guresberatcan.spaceflightnewsapp.features.home

import android.os.Bundle
import android.os.Parcelable
import android.view.View
import android.widget.SearchView
import android.widget.Toast
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

    // Timer for periodic article fetching
    private lateinit var timer: Timer

    // Flag to determine if it's the first time the fragment is opened
    private var isFirstOpen = true

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentHomeBinding.bind(view)
        initObservers()
        initViews()
    }

    // Initialize the timer when the fragment is resumed
    override fun onResume() {
        super.onResume()
        initializeTimer()
    }

    // Schedule a task to fetch articles periodically
    private fun initializeTimer() {
        timer = Timer()
        val task = object : TimerTask() {
            override fun run() {
                viewModel.getArticles()
            }
        }
        timer.scheduleAtFixedRate(task, FETCH_DELAY_MILLIS, FETCH_PERIOD_MILLIS)
    }

    // Initialize views and set up listeners
    private fun initViews() {
        binding.recyclerview.apply {
            // Set up the RecyclerView and its adapter
            adapter = articleAdapter
            // Set click listeners for article and favorite button
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
            // Toggle favorite status and update the database
            articleAdapter.favouriteClickedListener = { article, position ->
                article.isFavourite = article.isFavourite.not()
                viewModel.updateArticle(article.id, article.isFavourite)
                articleAdapter.notifyItemChanged(position)
            }
        }

        // Set up the SearchView
        binding.searchBar.clearFocus()
        binding.searchBar.setOnClickListener {
            binding.searchBar.isIconified = false
        }

        binding.searchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    // Trigger article filtering based on the search query
                    viewModel.filter(it)
                }
                return false
            }
        })
    }

    // Scroll to the saved position if it's the first time the fragment is opened
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

    // Set up observers to react to changes in the ViewModel's SharedFlow
    private fun initObservers() {
        lifecycleScope.launch {
            // Observe the articlesSharedFlow with lifecycle awareness
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.articlesSharedFlow.collect {
                        when (it) {
                            is com.guresberatcan.domain.utils.Resource.Success -> {
                                // Update the RecyclerView with the new list of articles
                                it.data?.let { it1 -> articleAdapter.submitList(it1) }
                                // Scroll to the saved position if it's the first time the fragment is opened
                                scrollToPosition()
                            }

                            is com.guresberatcan.domain.utils.Resource.Error -> {
                                // Show a toast message in case of an error
                                Toast.makeText(
                                    requireContext(),
                                    it.errorMessage,
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }
                }
            }
        }
    }

    // Clean up resources when the view is destroyed
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        // Cancel the timer to stop periodic article fetching
        timer.cancel()
    }

    companion object {
        // Constants for article fetching interval
        const val FETCH_PERIOD_MILLIS = 60000L
        const val FETCH_DELAY_MILLIS = 1L
    }
}