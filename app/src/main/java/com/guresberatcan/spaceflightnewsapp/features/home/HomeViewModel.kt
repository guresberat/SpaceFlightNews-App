package com.guresberatcan.spaceflightnewsapp.features.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.guresberatcan.domain.model.Article
import com.guresberatcan.domain.usecase.FilterUseCase
import com.guresberatcan.domain.usecase.GetArticlesUseCase
import com.guresberatcan.domain.usecase.UpdateArticleUseCase
import com.guresberatcan.domain.utils.Constants.SEARCH_DELAY_MILLIS
import com.guresberatcan.domain.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getArticlesUseCase: GetArticlesUseCase,
    private val updateArticleUseCase: UpdateArticleUseCase,
    private val filterUseCase: FilterUseCase
) : ViewModel() {

    val articlesSharedFlow: SharedFlow<Resource<List<Article>>>
        get() = _articlesSharedFlow
    private val _articlesSharedFlow =
        MutableSharedFlow<Resource<List<Article>>>(
            replay = 1,
            onBufferOverflow = BufferOverflow.DROP_OLDEST
        )

    // Job to handle delayed execution during text input for filtering
    private lateinit var textChangeCountDownJob: Job

    // Flag to prevent refreshing articles while search is ongoing
    private var isSearchOpened = false


    /**
     * Fetches articles from the use case and emits them to the shared flow,
     * unless the search is ongoing (isSearchOpened is true).
     */
    fun getArticles() = viewModelScope.launch {

        if (!isSearchOpened) {
            val list = getArticlesUseCase()
            _articlesSharedFlow.emit(list)
        }
    }

    /**
     * Updates the favorite status of an article.
     */
    fun updateArticle(article: Int?, favourite: Boolean) = viewModelScope.launch {
        article?.let { updateArticleUseCase(it, favourite) }
    }

    /**
     * Filters articles based on the provided search query and emits the filtered list
     * to the shared flow after a delay, preventing rapid updates during typing.
     */
    fun filter(searchQuery: String) = viewModelScope.launch {
        if (::textChangeCountDownJob.isInitialized)
            textChangeCountDownJob.cancel()

        textChangeCountDownJob = launch {
            isSearchOpened = if (searchQuery.isNotEmpty()) {
                delay(SEARCH_DELAY_MILLIS) // introduce a delay to avoid rapid updates during typing
                true
            } else {
                false
            }
            filterUseCase(searchQuery).collect {
                _articlesSharedFlow.emit(Resource.Success(it))
            }
        }
    }
}