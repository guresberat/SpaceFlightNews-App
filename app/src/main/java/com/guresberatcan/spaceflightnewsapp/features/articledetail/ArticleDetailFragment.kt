package com.guresberatcan.spaceflightnewsapp.features.articledetail

import android.os.Bundle
import android.os.Parcelable
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.guresberatcan.data.util.parcelable
import com.guresberatcan.domain.model.Article
import com.guresberatcan.domain.utils.Constants.BUNDLE_ARTICLE_ID
import com.guresberatcan.domain.utils.Constants.RECYCLERVIEW_INSTANCE_STATE
import com.guresberatcan.spaceflightnewsapp.R
import com.guresberatcan.spaceflightnewsapp.databinding.FragmentArticleDetailBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ArticleDetailFragment : Fragment(R.layout.fragment_article_detail) {

    private var _binding: FragmentArticleDetailBinding? = null
    private val binding: FragmentArticleDetailBinding get() = _binding!!

    private val viewModel: ArticleDetailViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentArticleDetailBinding.bind(view)
        // Initialize observers and listeners
        initObservers()
        initListeners()
    }

    private fun initListeners() {
        // Retrieve article ID from arguments
        val articleId = requireArguments().getInt(BUNDLE_ARTICLE_ID, -1)
        // If article ID is not valid, navigate back to the FirstFragment
        if (articleId == -1) {
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        } else {
            // Fetch article data based on the provided ID
            viewModel.getArticleData(articleId)
        }

        // Retrieve RecyclerView instance state data from arguments
        val positionData = try {
            requireArguments().parcelable<Parcelable>(RECYCLERVIEW_INSTANCE_STATE)
        } catch (e: Exception) {
            null
        }

        // Set up navigation back to the HomeFragment when the navigation icon is clicked
        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigate(
                R.id.action_SecondFragment_to_FirstFragment,
                Bundle().apply {
                    putParcelable(RECYCLERVIEW_INSTANCE_STATE, positionData)
                })
        }
    }

    // Initialize observers to update the UI with fetched article data
    private fun initObservers() {
        lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.articleSharedFlow.collect {
                        setViewData(it)
                    }
                }
            }
        }
    }

    // Set the fetched article data to the corresponding views in the layout
    private fun setViewData(article: Article) {
        with(binding) {
            Glide.with(requireActivity()).load(article.imageUrl)
                .into(expandedImage)
            toolbarLayout.title = article.title
            textviewSecond.text = article.summary
        }
    }

    // Clean up resources when the view is destroyed
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}