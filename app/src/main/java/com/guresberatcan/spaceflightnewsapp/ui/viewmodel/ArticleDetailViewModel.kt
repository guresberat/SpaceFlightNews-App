package com.guresberatcan.spaceflightnewsapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.guresberatcan.spaceflightnewsapp.data.model.Article
import com.guresberatcan.spaceflightnewsapp.data.usecase.GetArticleDataUseCase
import com.guresberatcan.spaceflightnewsapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArticleDetailViewModel @Inject constructor(
    private val getArticleDataUseCase: GetArticleDataUseCase
) : ViewModel() {

    val articlesSharedFlow: SharedFlow<Article>
        get() = _articlesSharedFlow
    private val _articlesSharedFlow =
        MutableSharedFlow<Article>(extraBufferCapacity = 1)

    fun getArticleData(id: Int) = viewModelScope.launch {
        getArticleDataUseCase(id).collect {
            _articlesSharedFlow.emit(it)
        }
    }
}