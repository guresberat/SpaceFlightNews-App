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

    // SharedFlow to emit article data to the UI
    val articleSharedFlow: SharedFlow<com.guresberatcan.domain.model.Article>
        get() = _articleSharedFlow
    private val _articleSharedFlow =
        MutableSharedFlow<com.guresberatcan.domain.model.Article>(
            replay = 1,
            onBufferOverflow = BufferOverflow.SUSPEND
        )

    /**
     * Fetches article data based on the provided article ID using the getArticleDataUseCase,
     * and emits the result to the articleSharedFlow for UI consumption.
     *
     * @param id The ID of the article to fetch.
     */
    fun getArticleData(id: Int) = viewModelScope.launch {
        // Use the getArticleDataUseCase to fetch article data
        getArticleDataUseCase(id).collect {
            // Emit the result to the articleSharedFlow for UI consumption
            _articleSharedFlow.emit(it)
        }
    }
}