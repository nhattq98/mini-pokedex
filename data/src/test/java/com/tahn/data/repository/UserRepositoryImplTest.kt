// package com.tahn.data.repository
//
// import androidx.paging.ExperimentalPagingApi
// import androidx.paging.testing.asPagingSourceFactory
// import androidx.paging.testing.asSnapshot
// import androidx.room.withTransaction
// import com.tahn.data.local.database.AppDatabase
// import com.tahn.data.local.storage.AppPreferences
// import io.mockk.MockKAnnotations
// import io.mockk.Runs
// import io.mockk.coEvery
// import io.mockk.just
// import io.mockk.mockk
// import io.mockk.mockkStatic
// import io.mockk.slot
// import junit.framework.TestCase.assertEquals
// import kotlinx.coroutines.flow.first
// import kotlinx.coroutines.test.runTest
// import org.junit.Before
// import org.junit.Test
// import org.junit.runner.RunWith
// import org.robolectric.RobolectricTestRunner
//
// @RunWith(RobolectricTestRunner::class)
// internal class UserRepositoryImplTest {
//    private lateinit var userServices: UserServices
//    private lateinit var database: AppDatabase
//    private lateinit var appPreferences: AppPreferences
//    private lateinit var repository: UserRepositoryImpl
//    private lateinit var remoteMediator: UserRemoteMediator
//
//    @Before
//    fun setup() {
//        MockKAnnotations.init(this)
//        mockkStatic("androidx.room.RoomDatabaseKt")
//        userServices = mockk()
//        database = mockk()
//        appPreferences = mockk()
//        repository = UserRepositoryImpl(userServices, database, appPreferences)
//        remoteMediator = mockk()
//    }
//
//    @OptIn(ExperimentalPagingApi::class)
//    @Test
//    fun `getUserPaging returns PagingData`() =
//        runTest {
//            val userResponses = (0..10).mockListUserResponse()
//            val items = userResponses.map { it.toUserEntity() }
//            val pagingSourceFactory = items.asPagingSourceFactory()
//            val pagingSource = pagingSourceFactory()
//            val expectedPagingData = items.map { it.toUserModel() }
//            coEvery {
//                userServices.getUsers(any(), any())
//            } returns userResponses
//
//            coEvery { database.userDao().insertUsers(any()) } just Runs
//            coEvery { database.userDao().clearAll() } just Runs
//            coEvery { database.userRemoteKeyDao().clearAll() } just Runs
//            coEvery { database.userRemoteKeyDao().insertAll(any()) } just Runs
//            coEvery { appPreferences.saveValue(any(), any<Long>()) } just Runs
//            val transactionLambda = slot<suspend () -> Unit>()
//            coEvery { database.withTransaction(capture(transactionLambda)) } coAnswers {
//                transactionLambda.captured.invoke()
//            }
//            coEvery { database.userDao().getUserPagingSource() } returns pagingSource
//            coEvery { appPreferences.getValue(any(), any<Long>()) } returns 0
//
//            // When
//            val result = repository.getUserPaging().asSnapshot()
//
//            assertEquals(result, expectedPagingData)
//        }
//
//    @Test
//    fun `getUserDetails returns UserDetailsModel`() =
//        runTest {
//            // Given
//            val username = "mojombo"
//            val apiResponse =
//                UserDetailsResponse(
//                    login = "mojombo",
//                    id = 1,
//                    avatarUrl = "https://avatars.githubusercontent.com/u/1?v=4",
//                    htmlUrl = "https://github.com/mojombo",
//                    blog = "https://github.com/mojombo",
//                    location = "San Francisco",
//                    followers = 24205,
//                    following = 11,
//                )
//            val expectedModel = apiResponse.toUserDetailsModel()
//
//            coEvery { userServices.getUserDetails(username) } returns apiResponse
//
//            // When
//            val result = repository.getUserDetails(username).first()
//
//            // Then
//            assertEquals(expectedModel, result)
//        }
//
//    private fun IntRange.mockListUserResponse(): List<UserResponse> =
//        this.map {
//            UserResponse(
//                id = it,
//                login = "username$it",
//                avatarUrl = "avatarUrl$it",
//                htmlUrl = "htmlUrl$it",
//            )
//        }
// }
