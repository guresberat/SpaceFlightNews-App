package com.guresberatcan.spaceflightnewsapp.features.home

import com.guresberatcan.domain.model.Article
import com.guresberatcan.domain.usecase.FilterUseCase
import com.guresberatcan.domain.usecase.GetArticlesUseCase
import com.guresberatcan.domain.usecase.UpdateArticleUseCase
import com.guresberatcan.domain.utils.Resource
import com.guresberatcan.spaceflightnewsapp.util.BaseTest
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class HomeViewModelTest : BaseTest() {


    private lateinit var viewModel: HomeViewModel
    private lateinit var getArticlesUseCase: GetArticlesUseCase
    private lateinit var updateArticleUseCase: UpdateArticleUseCase
    private lateinit var filterUseCase: FilterUseCase

    @Before
    fun setup() {
        getArticlesUseCase = mockk()
        updateArticleUseCase = mockk()
        filterUseCase = mockk()

        viewModel = HomeViewModel(getArticlesUseCase, updateArticleUseCase, filterUseCase)
    }

    @Test
    fun `getArticles should emit articles from use case`() = runTest {
        // Given
        val fakeArticles = listOf(Article(id = 1), Article(id = 2))
        coEvery { getArticlesUseCase() } returns Resource.Success(fakeArticles)


        // When
        viewModel.getArticles()
        advanceUntilIdle()
        // Then
        val actual = viewModel.articlesSharedFlow.replayCache.firstOrNull()
        assertTrue(actual is Resource.Success && actual.data == fakeArticles)
    }

    @Test
    fun `updateArticle should call updateArticleUseCase`() = runTest {
        // Given
        val articleId = 1
        val favourite = true
        coEvery { updateArticleUseCase(articleId, favourite) } returns Unit

        // When
        viewModel.updateArticle(articleId, favourite)
        advanceUntilIdle()
        // Then
        coVerify(exactly = 1) { updateArticleUseCase(articleId, favourite) }
    }

    @Test
    fun `filter should emit filtered articles from use case`() = runTest {
        // Given
        val searchQuery = "query"
        val fakeFilteredArticles = listOf(Article(id = 1))
        coEvery { filterUseCase(searchQuery) } returns flowOf(fakeFilteredArticles)

        // When
        viewModel.filter(searchQuery)
        advanceUntilIdle()

        // Then
        val actual = viewModel.articlesSharedFlow.replayCache.firstOrNull()
        assertTrue(actual is Resource.Success && actual.data == fakeFilteredArticles)
    }

    @Test
    fun `filter should emit filtered articles from use case when the searchQuery is empty`() =
        runTest {
            // Given
            val searchQuery = ""
            val fakeFilteredArticles = listOf(Article(id = 1))
            coEvery { filterUseCase(searchQuery) } returns flowOf(fakeFilteredArticles)

            // When
            viewModel.filter(searchQuery)
            advanceUntilIdle()

            // Then
            val actual = viewModel.articlesSharedFlow.replayCache.firstOrNull()
            assertTrue(actual is Resource.Success && actual.data == fakeFilteredArticles)
        }

    @Test
    fun `textChangeCountDownJob should cancel when there is a second function call`() =
        runTest {
            // Given
            val fakeFilteredArticles = listOf(Article(id = 1))
            coEvery { filterUseCase(any()) } returns flowOf(fakeFilteredArticles)

            // When
            viewModel.filter("")
            advanceUntilIdle()

            viewModel.filter("searchQuery")
            advanceUntilIdle()

            // Then
            val actual = viewModel.articlesSharedFlow.replayCache.firstOrNull()
            assertTrue(actual is Resource.Success && actual.data == fakeFilteredArticles)
        }
}
