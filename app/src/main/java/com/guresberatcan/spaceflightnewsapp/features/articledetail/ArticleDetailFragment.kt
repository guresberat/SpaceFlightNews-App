package com.guresberatcan.spaceflightnewsapp.features.articledetail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.guresberatcan.spaceflightnewsapp.R
import com.guresberatcan.spaceflightnewsapp.databinding.FragmentSecondBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ArticleDetailFragment : Fragment(R.layout.fragment_article_detail) {

    private var _binding: FragmentSecondBinding? = null
    private val viewModel: ArticleDetailViewModel by viewModels()

    private val binding : FragmentSecondBinding get() = _binding!!


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentSecondBinding.bind(view)
        initObservers()
        viewModel.getArticleData(requireArguments().getInt("id"))

        binding.toolbar.setNavigationIcon(com.google.android.material.R.drawable.ic_arrow_back_black_24)
        binding.toolbar.title = "Flight Detail"
        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun initObservers() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                launch {
                    viewModel.articlesSharedFlow.collect {
                        setViewData(it)
                    }
                }
            }
        }
    }

    private fun setViewData(article: com.guresberatcan.domain.model.Article) {
        with(binding) {
            Glide.with(requireActivity()).load(article.imageUrl)
                .into(expandedImage)
            toolbarLayout.title = article.title
            textviewSecond.text = article.summary
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}