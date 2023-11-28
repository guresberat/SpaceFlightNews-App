package com.guresberatcan.spaceflightnewsapp.features.articledetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.guresberatcan.domain.usecase.GetArticleDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArticleDetailViewModel @Inject constructor(
    private val getArticleDataUseCase: GetArticleDataUseCase
) : ViewModel() {

    val articleSharedFlow: SharedFlow<com.guresberatcan.domain.model.Article>
        get() = _articleSharedFlow
    private val _articleSharedFlow =
        MutableSharedFlow<com.guresberatcan.domain.model.Article>(replay = 1, onBufferOverflow = BufferOverflow.SUSPEND)

    fun getArticleData(id: Int) = viewModelScope.launch {
        getArticleDataUseCase(id).collect {
            _articleSharedFlow.emit(it)
        }
    }
}