package com.guresberatcan.spaceflightnewsapp.features.articledetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArticleDetailViewModel @Inject constructor(
    private val getArticleDataUseCase: com.guresberatcan.domain.usecase.GetArticleDataUseCase
) : ViewModel() {

    val articlesSharedFlow: SharedFlow<com.guresberatcan.domain.model.Article>
        get() = _articlesSharedFlow
    private val _articlesSharedFlow =
        MutableSharedFlow<com.guresberatcan.domain.model.Article>(extraBufferCapacity = 1)

    fun getArticleData(id: Int) = viewModelScope.launch {
        getArticleDataUseCase(id).collect {
            _articlesSharedFlow.emit(it)
        }
    }
}