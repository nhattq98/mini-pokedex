// package com.tahn.minipokedex.ui.feature.home
//
// import androidx.paging.AsyncPagingDataDiffer
// import androidx.paging.PagingData
// import androidx.recyclerview.widget.DiffUtil
// import androidx.recyclerview.widget.ListUpdateCallback
// import com.tahn.domain.provider.DispatcherProvider
// import com.tahn.domain.usecase.GetUserPagingSourceUseCase
// import io.mockk.every
// import io.mockk.mockk
// import junit.framework.TestCase.assertEquals
// import kotlinx.coroutines.Dispatchers
// import kotlinx.coroutines.ExperimentalCoroutinesApi
// import kotlinx.coroutines.flow.collectLatest
// import kotlinx.coroutines.flow.flowOf
// import kotlinx.coroutines.launch
// import kotlinx.coroutines.test.UnconfinedTestDispatcher
// import kotlinx.coroutines.test.advanceUntilIdle
// import kotlinx.coroutines.test.resetMain
// import kotlinx.coroutines.test.runTest
// import kotlinx.coroutines.test.setMain
// import org.junit.After
// import org.junit.Before
// import org.junit.Test
//
// @OptIn(ExperimentalCoroutinesApi::class)
// internal class HomeViewModelTest {
//    private val testDispatcher = UnconfinedTestDispatcher()
//    private lateinit var getUserPagingSourceUseCase: GetUserPagingSourceUseCase
//    private lateinit var dispatcherProvider: DispatcherProvider
//    private lateinit var viewModel: HomeViewModel
//
//    @Before
//    fun setup() {
//        Dispatchers.setMain(testDispatcher)
//        getUserPagingSourceUseCase = mockk()
//        dispatcherProvider = mockk()
//        every { dispatcherProvider.io() } returns testDispatcher
//    }
//
//    @After
//    fun tearDown() {
//        Dispatchers.resetMain()
//    }
//
//    @Test
//    fun `userPagingSource emits correct paging data`() =
//        runTest {
//            // Given
//            val userModelList =
//                (1..5).map {
//                    UserModel(id = it, username = "User$it", avatarUrl = "avatarUrl", htmlUrl = "htmlUrl")
//                }
//            val pagingData = PagingData.from(userModelList)
//            every { getUserPagingSourceUseCase.invoke() } returns flowOf(pagingData)
//
//            viewModel = HomeViewModel(getUserPagingSourceUseCase, dispatcherProvider)
//
//            val differ =
//                AsyncPagingDataDiffer(
//                    diffCallback = diffCallback,
//                    updateCallback = noListCallback,
//                    mainDispatcher = testDispatcher,
//                    workerDispatcher = testDispatcher,
//                )
//
//            // When
//            val job =
//                launch {
//                    viewModel.userPagingSource.collectLatest { pagingData ->
//                        differ.submitData(pagingData)
//                    }
//                }
//
//            advanceUntilIdle()
//
//            // Then
//            assertEquals(userModelList, differ.snapshot())
//            job.cancel()
//        }
//
//    private val diffCallback =
//        object : DiffUtil.ItemCallback<UserModel>() {
//            override fun areItemsTheSame(
//                oldItem: UserModel,
//                newItem: UserModel,
//            ) = oldItem.id == newItem.id
//
//            override fun areContentsTheSame(
//                oldItem: UserModel,
//                newItem: UserModel,
//            ) = oldItem == newItem
//        }
//
//    private val noListCallback =
//        object : ListUpdateCallback {
//            override fun onInserted(
//                position: Int,
//                count: Int,
//            ) {}
//
//            override fun onRemoved(
//                position: Int,
//                count: Int,
//            ) {}
//
//            override fun onMoved(
//                fromPosition: Int,
//                toPosition: Int,
//            ) {}
//
//            override fun onChanged(
//                position: Int,
//                count: Int,
//                payload: Any?,
//            ) {}
//        }
// }
