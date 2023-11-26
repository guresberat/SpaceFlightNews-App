package com.guresberatcan.spaceflightnewsapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.guresberatcan.spaceflightnewsapp.data.model.Article
import com.guresberatcan.spaceflightnewsapp.data.usecase.FilterUseCase
import com.guresberatcan.spaceflightnewsapp.data.usecase.GetArticlesUseCase
import com.guresberatcan.spaceflightnewsapp.data.usecase.UpdateArticleUseCase
import com.guresberatcan.spaceflightnewsapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FlightListViewModel @Inject constructor(
    private val getArticlesUseCase: GetArticlesUseCase,
    private val updateArticleUseCase: UpdateArticleUseCase,
    private val filterUseCase: FilterUseCase
) : ViewModel() {

    val articlesSharedFlow: SharedFlow<Resource<List<Article>>>
        get() = _articlesSharedFlow
    private val _articlesSharedFlow =
        MutableSharedFlow<Resource<List<Article>>>(extraBufferCapacity = 1)

    private lateinit var textChangeCountDownJob: Job

    //to prevent refresh while search is open
    private var isSearchOpened = false


    fun getArticles() = viewModelScope.launch {
        getArticlesUseCase().collect {
            if (!isSearchOpened) {
                _articlesSharedFlow.emit(it)
            }
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
                _articlesSharedFlow.emit(Resource.Success(it))
            }
        }
    }
}