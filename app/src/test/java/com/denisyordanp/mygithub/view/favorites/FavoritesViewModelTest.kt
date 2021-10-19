package com.denisyordanp.mygithub.view.favorites

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.denisyordanp.mygithub.data.repository.favorites.FakeFavoriteRepository
import com.denisyordanp.mygithub.utils.MainCoroutineRule
import com.denisyordanp.mygithub.utils.getOrAwaitValue
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class FavoritesViewModelTest {

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    val instantExecutor = InstantTaskExecutorRule()

    private lateinit var repository: FakeFavoriteRepository
    private lateinit var favoriteViewModel: FavoritesViewModel

    @Before
    fun setUp() {
        repository = FakeFavoriteRepository()
        favoriteViewModel = FavoritesViewModel(repository)
    }

    @Test
    fun `favorites not empty when favorites available on repository`() =
        mainCoroutineRule.runBlockingTest {
            repository.insertFavorite(FakeFavoriteRepository.fakeFavorite)

            val favorites = favoriteViewModel.favorites.getOrAwaitValue()
            assertThat(favorites).isNotEmpty()
        }

    @Test
    fun `favorites empty when favorites not available on repository`() =
        mainCoroutineRule.runBlockingTest {
            val favorites = favoriteViewModel.favorites.getOrAwaitValue()
            assertThat(favorites).isEmpty()
        }

    @Test
    fun `isFavoriteEmpty return false when favorites is not empty`() =
        mainCoroutineRule.runBlockingTest {
            repository.insertFavorite(FakeFavoriteRepository.fakeFavorite)

            val isEmpty = favoriteViewModel.isFavoritesEmpty.getOrAwaitValue()
            assertThat(isEmpty).isFalse()
        }

    @Test
    fun `isFavoriteEmpty return true when favorites empty`() {
        val isEmpty = favoriteViewModel.isFavoritesEmpty.getOrAwaitValue()
        assertThat(isEmpty).isTrue()
    }

    @Test
    fun `insert favorite then delete it should return empty`() = mainCoroutineRule.runBlockingTest {
        repository.insertFavorite(FakeFavoriteRepository.fakeFavorite)

        favoriteViewModel.removeFavorite(FakeFavoriteRepository.fakeFavorite)

        val favorites = favoriteViewModel.favorites.getOrAwaitValue()
        assertThat(favorites).isEmpty()
    }
}