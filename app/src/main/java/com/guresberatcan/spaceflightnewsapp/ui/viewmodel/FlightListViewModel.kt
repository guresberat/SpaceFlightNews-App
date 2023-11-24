package com.guresberatcan.spaceflightnewsapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.guresberatcan.spaceflightnewsapp.data.model.Article
import com.guresberatcan.spaceflightnewsapp.data.usecase.GetArticlesUseCase
import com.guresberatcan.spaceflightnewsapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FlightListViewModel @Inject constructor(
    private val getArticlesUseCase: GetArticlesUseCase
) : ViewModel() {

    val articlesSharedFlow: SharedFlow<Resource<List<Article?>?>>
        get() = _articlesSharedFlow
    private val _articlesSharedFlow = MutableSharedFlow<Resource<List<Article?>?>>()

    init {
        getArticles()
    }

    private fun getArticles() = viewModelScope.launch {
        _articlesSharedFlow.emit(getArticlesUseCase())
    }
}