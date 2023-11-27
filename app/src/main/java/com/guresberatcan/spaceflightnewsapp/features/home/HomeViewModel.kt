package com.guresberatcan.spaceflightnewsapp.features.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
    private val getArticlesUseCase: com.guresberatcan.domain.usecase.GetArticlesUseCase,
    private val updateArticleUseCase: com.guresberatcan.domain.usecase.UpdateArticleUseCase,
    private val filterUseCase: com.guresberatcan.domain.usecase.FilterUseCase
) : ViewModel() {

    val articlesSharedFlow: SharedFlow<com.guresberatcan.domain.utils.Resource<List<com.guresberatcan.domain.model.Article>>>
        get() = _articlesSharedFlow
    private val _articlesSharedFlow =
        MutableSharedFlow<com.guresberatcan.domain.utils.Resource<List<com.guresberatcan.domain.model.Article>>>(
            replay = 1,
            onBufferOverflow = BufferOverflow.DROP_OLDEST
        )


    private lateinit var textChangeCountDownJob: Job

    //to prevent refresh while search is open
    private var isSearchOpened = false


    fun getArticles() = viewModelScope.launch {

        if (!isSearchOpened) {
            val list = getArticlesUseCase()
            _articlesSharedFlow.emit(list)
        }
    }

    fun updateArticle(article: Int?, favourite: Boolean) = viewModelScope.launch {
        article?.let { updateArticleUseCase(it, favourite) }
    }

    fun filter(searchQuery: String) = viewModelScope.launch {
        if (::textChangeCountDownJob.isInitialized)
            textChangeCountDownJob.cancel()

        textChangeCountDownJob = launch {
            isSearchOpened = if (searchQuery.isNotEmpty()) {
                delay(800)
                true
            } else {
                false
            }
            filterUseCase(searchQuery).collect {
                _articlesSharedFlow.emit(com.guresberatcan.domain.utils.Resource.Success(it))
            }
        }
    }
}