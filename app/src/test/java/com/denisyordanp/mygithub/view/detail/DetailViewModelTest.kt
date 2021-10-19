package com.denisyordanp.mygithub.view.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.denisyordanp.mygithub.data.repository.favorites.FakeFavoriteRepository
import com.denisyordanp.mygithub.data.repository.users.FakeUserRepository
import com.denisyordanp.mygithub.utils.MainCoroutineRule
import com.denisyordanp.mygithub.utils.getOrAwaitValue
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class DetailViewModelTest {

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    val instantExecutor = InstantTaskExecutorRule()

    private lateinit var favoriteRepository: FakeFavoriteRepository
    private lateinit var userRepository: FakeUserRepository
    private lateinit var detailViewModel: DetailViewModel

    @Before
    fun setUp() {
        favoriteRepository = FakeFavoriteRepository()
        userRepository = FakeUserRepository()
        detailViewModel = DetailViewModel(favoriteRepository, userRepository)
    }

    @Test
    fun `request data then user all response should same as requested`() =
        mainCoroutineRule.runBlockingTest {
            detailViewModel.requestUserData("username")

            val detailUser = detailViewModel.detailUser.getOrAwaitValue()
            val followers = detailViewModel.followers.getOrAwaitValue()
            val followings = detailViewModel.followings.getOrAwaitValue()
            val repositories = detailViewModel.repositories.getOrAwaitValue()

            assertThat(detailUser).isEqualTo(FakeUserRepository.fakeDetailUser)
            assertThat(followers).isEqualTo(FakeUserRepository.fakeFollowUsers)
            assertThat(followings).isEqualTo(FakeUserRepository.fakeFollowUsers)
            assertThat(repositories).isEqualTo(FakeUserRepository.fakeRepositories)
        }

    @Test
    fun `request error data then show showSnackEvent should not null`() =
        mainCoroutineRule.runBlockingTest {
            userRepository.shouldReturnError(true)
            detailViewModel.requestUserData("username")

            val snackBarEvent = detailViewModel.showSnackEvent.getOrAwaitValue()

            assertThat(snackBarEvent).isNotNull()
        }

    @Test
    fun `setFavoriteUser then get favorite by username should return true`() =
        mainCoroutineRule.runBlockingTest {
            detailViewModel.setFavoriteUser(FakeUserRepository.fakeDetailUser, false)

            val isFavorite = detailViewModel.isFavorite(FakeUserRepository.fakeDetailUser.username)
                .getOrAwaitValue()
            assertThat(isFavorite).isTrue()
        }

    @Test
    fun `setFavoriteUser then get favorite by other username should return false`() =
        mainCoroutineRule.runBlockingTest {
            detailViewModel.setFavoriteUser(FakeUserRepository.fakeDetailUser, false)

            val isFavorite = detailViewModel.isFavorite("false username").getOrAwaitValue()
            assertThat(isFavorite).isFalse()
        }
}