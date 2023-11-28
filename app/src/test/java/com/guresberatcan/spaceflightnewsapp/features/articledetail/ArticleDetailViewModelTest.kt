package com.guresberatcan.spaceflightnewsapp.features.articledetail

import com.guresberatcan.domain.model.Article
import com.guresberatcan.domain.usecase.GetArticleDataUseCase
import com.guresberatcan.spaceflightnewsapp.util.BaseTest
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ArticleDetailViewModelTest : BaseTest() {

    private lateinit var viewModel: ArticleDetailViewModel
    private lateinit var getArticleDataUseCase: GetArticleDataUseCase

    @Before
    fun setup() {
        getArticleDataUseCase = mockk()
        viewModel = ArticleDetailViewModel(getArticleDataUseCase)
    }

    @Test
    fun `getArticleData emits article from use case`() = runTest {
        // Given a fake article with id 1
        val fakeArticle = Article(
            id = 1
        )
        // Stub the use case to return a flow of the fake article when invoked with id 1
        coEvery { getArticleDataUseCase(1) } returns flow {
            emit(fakeArticle)
        }
        val expected = mutableListOf<Article>()

        val job = launch {
            viewModel.articleSharedFlow.toList(expected)
        }
        viewModel.getArticleData(1)

        advanceUntilIdle()

        assertEquals(fakeArticle, expected.first())

        job.cancel()
    }

    @Test
    fun `getArticleData should call getArticleDataUseCase`() = runTest {
        // Given
        val articleId = 1

        coEvery { getArticleDataUseCase(articleId) } returns flow {
            emit(Article())
        }

        // When
        viewModel.getArticleData(articleId)
        advanceUntilIdle()

        // Then
        verify(exactly = 1) { getArticleDataUseCase(articleId) }
    }
}