// package com.tahn.minipokedex.ui.feature.user_detail
//
// import androidx.arch.core.executor.testing.InstantTaskExecutorRule
// import androidx.lifecycle.Observer
// import com.tahn.domain.model.error.Failure
// import com.tahn.domain.provider.DispatcherProvider
// import io.mockk.every
// import io.mockk.mockk
// import junit.framework.TestCase.assertEquals
// import junit.framework.TestCase.assertTrue
// import kotlinx.coroutines.Dispatchers
// import kotlinx.coroutines.ExperimentalCoroutinesApi
// import kotlinx.coroutines.flow.flow
// import kotlinx.coroutines.flow.flowOf
// import kotlinx.coroutines.test.UnconfinedTestDispatcher
// import kotlinx.coroutines.test.advanceUntilIdle
// import kotlinx.coroutines.test.resetMain
// import kotlinx.coroutines.test.runTest
// import kotlinx.coroutines.test.setMain
// import org.junit.After
// import org.junit.Before
// import org.junit.Rule
// import org.junit.Test
// import java.io.IOException
//
// @OptIn(ExperimentalCoroutinesApi::class)
// internal class UserDetailsViewModelTest {
//    private val testDispatcher = UnconfinedTestDispatcher()
//    private lateinit var getUserDetailsUseCase: GetUserDetailsUseCase
//    private lateinit var dispatcherProvider: DispatcherProvider
//    private lateinit var viewModel: UserDetailsViewModel
//
//    @get:Rule
//    val instantTaskExecutorRule = InstantTaskExecutorRule()
//
//    @Before
//    fun setup() {
//        Dispatchers.setMain(testDispatcher)
//        getUserDetailsUseCase = mockk()
//        dispatcherProvider = mockk()
//        every { dispatcherProvider.io() } returns testDispatcher
//        viewModel = UserDetailsViewModel(getUserDetailsUseCase, dispatcherProvider)
//    }
//
//    @After
//    fun tearDown() {
//        Dispatchers.resetMain()
//    }
//
//    @Test
//    fun `getUserDetails should update userDetails and loading states`() =
//        runTest {
//            // Given
//            val username = "test_user"
//            val expected =
//                UserDetailsModel(
//                    id = 1,
//                    username = "test_user",
//                    blog = "Android Dev",
//                    avatarUrl = "https://example.com/avatar",
//                    htmlUrl = "https://github.com/testuser",
//                    followers = 0,
//                    followings = 0,
//                    location = "Test",
//                )
//
//            every { getUserDetailsUseCase(username) } returns flowOf(expected)
//
//            val observer = Observer<UserDetailsModel> {}
//            viewModel.userDetails.observeForever(observer)
//
//            // When
//            viewModel.getUserDetails(username)
//            advanceUntilIdle()
//
//            // Then
//            assertEquals(expected, viewModel.userDetails.value)
//            assertEquals(false, viewModel.loading.value)
//
//            viewModel.userDetails.removeObserver(observer)
//        }
//
//    @Test
//    fun `getUserDetails should post error on failure`() =
//        runTest {
//            // Given
//            val username = "error_user"
//            val expectedMessage = "Network error"
//            val exception = IOException(expectedMessage)
//
//            every { getUserDetailsUseCase(username) } returns flow { throw exception }
//
//            val errorObserver = Observer<Throwable> {}
//            viewModel.error.observeForever(errorObserver)
//
//            // When
//            viewModel.getUserDetails(username)
//            advanceUntilIdle()
//
//            // Then
//            assertTrue(viewModel.error.value is Failure.NetworkFailure)
//            assertEquals(
//                expectedMessage,
//                (viewModel.error.value as Failure.NetworkFailure).errorMessage,
//            )
//            assertEquals(false, viewModel.loading.value)
//
//            viewModel.error.removeObserver(errorObserver)
//        }
// }
