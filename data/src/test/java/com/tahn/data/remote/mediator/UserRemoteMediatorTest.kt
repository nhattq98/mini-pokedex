// package com.tahn.data.remote.mediator
//
// import androidx.paging.ExperimentalPagingApi
// import androidx.paging.LoadType
// import androidx.paging.PagingConfig
// import androidx.paging.PagingSource.LoadResult.Page
// import androidx.paging.PagingState
// import androidx.paging.RemoteMediator.InitializeAction
// import androidx.paging.RemoteMediator.MediatorResult
// import androidx.room.withTransaction
// import com.tahn.data.local.database.AppDatabase
// import com.tahn.data.local.database.dao.UserDao
// import com.tahn.data.local.storage.AppPreferenceKey
// import com.tahn.data.local.storage.AppPreferences
// import io.mockk.MockKAnnotations
// import io.mockk.coEvery
// import io.mockk.coVerify
// import io.mockk.every
// import io.mockk.mockk
// import io.mockk.mockkStatic
// import io.mockk.slot
// import junit.framework.TestCase.assertTrue
// import kotlinx.coroutines.test.runTest
// import org.junit.Before
// import org.junit.Test
// import java.util.concurrent.TimeUnit
//
// @ExperimentalPagingApi
// internal class UserRemoteMediatorTest {
//    private lateinit var userServices: UserServices
//    private lateinit var database: AppDatabase
//    private lateinit var appPreferences: AppPreferences
//    private lateinit var userDao: UserDao
//    private lateinit var userRemoteKeyDao: UserRemoteKeyDao
//    private lateinit var userRemoteMediator: UserRemoteMediator
//
//    @Before
//    fun setup() {
//        MockKAnnotations.init(this)
//        mockkStatic("androidx.room.RoomDatabaseKt")
//        userServices = mockk()
//        database = mockk(relaxed = true)
//
//        appPreferences = mockk()
//        userDao = mockk(relaxed = true)
//        userRemoteKeyDao = mockk(relaxed = true)
//
//        every { database.userDao() } returns userDao
//        every { database.userRemoteKeyDao() } returns userRemoteKeyDao
//        userRemoteMediator = UserRemoteMediator(database, userServices, appPreferences)
//    }
//
//    @Test
//    fun `initialize returns SKIP_INITIAL_REFRESH`() =
//        runTest {
//            // Given
//            val currentTime = System.currentTimeMillis()
//            val inCachedTime = currentTime - TimeUnit.MILLISECONDS.convert(20, TimeUnit.MINUTES)
//            every {
//                appPreferences.getValue(
//                    AppPreferenceKey.LONG_LAST_TIME_FETCH_USER,
//                    any<Long>(),
//                )
//            } returns inCachedTime
//
//            // When
//            val result = userRemoteMediator.initialize()
//
//            // Then
//            assertTrue(result == InitializeAction.SKIP_INITIAL_REFRESH)
//        }
//
//    @Test
//    fun `initialize returns LAUNCH_INITIAL_REFRESH`() =
//        runTest {
//            // Given
//            val currentTime = System.currentTimeMillis()
//            val outCachedTime = currentTime - TimeUnit.MILLISECONDS.convert(31, TimeUnit.MINUTES)
//            coEvery {
//                appPreferences.getValue(
//                    AppPreferenceKey.LONG_LAST_TIME_FETCH_USER,
//                    0L,
//                )
//            } returns outCachedTime
//
//            // When
//            val result = userRemoteMediator.initialize()
//
//            // Then
//            assertTrue(result == InitializeAction.LAUNCH_INITIAL_REFRESH)
//        }
//
//    @Test
//    fun `refresh returns Success`() =
//        runTest {
//            // Given
//            val pagingState = createPagingState()
//            val userResponses = (0..20).mockListUserResponse()
//            coEvery { userServices.getUsers(any(), any()) } returns userResponses
//            coEvery {
//                appPreferences.saveValue(
//                    AppPreferenceKey.LONG_LAST_TIME_FETCH_USER,
//                    any<Long>(),
//                )
//            } returns Unit
//            val transactionLambda = slot<suspend () -> Unit>()
//            coEvery { database.withTransaction(capture(transactionLambda)) } coAnswers {
//                transactionLambda.captured.invoke()
//            }
//
//            // When
//            val result =
//                userRemoteMediator.load(
//                    LoadType.REFRESH,
//                    pagingState,
//                )
//
//            // Then
//            assertTrue(result is MediatorResult.Success)
//            assertTrue((result as MediatorResult.Success).endOfPaginationReached == false)
//            coVerify { userDao.clearAll() }
//            coVerify { userDao.insertUsers(any()) }
//            coVerify { userRemoteKeyDao.clearAll() }
//            coVerify { userRemoteKeyDao.insertAll(any()) }
//        }
//
//    @Test
//    fun `refresh returns Success with empty data`() =
//        runTest {
//            // Given
//            val pagingState = createPagingState()
//            val userResponses = emptyList<UserResponse>()
//            coEvery { userServices.getUsers(any(), any()) } returns userResponses
//            coEvery {
//                appPreferences.saveValue(
//                    AppPreferenceKey.LONG_LAST_TIME_FETCH_USER,
//                    any<Long>(),
//                )
//            } returns Unit
//            val transactionLambda = slot<suspend () -> Unit>()
//            coEvery { database.withTransaction(capture(transactionLambda)) } coAnswers {
//                transactionLambda.captured.invoke()
//            }
//
//            // When
//            val result =
//                userRemoteMediator.load(
//                    LoadType.REFRESH,
//                    pagingState,
//                )
//
//            // Then
//            assertTrue(result is MediatorResult.Success)
//            assertTrue((result as MediatorResult.Success).endOfPaginationReached)
//            coVerify { userDao.clearAll() }
//            coVerify { userRemoteKeyDao.clearAll() }
//        }
//
//    @Test
//    fun `refresh returns Error`() =
//        runTest {
//            // Given
//            val pagingState = createPagingState()
//            coEvery { userServices.getUsers(any(), any()) } throws Exception()
//
//            // When
//            val result = userRemoteMediator.load(LoadType.REFRESH, pagingState)
//
//            // Then
//            assertTrue(result is MediatorResult.Error)
//        }
//
//    @Test
//    fun `load APPEND returns Success with endOfPaginationReached false when lastItemOrNull is null`() =
//        runTest {
//            // Given
//            val pagingState = createPagingState()
//
//            // When
//            val result =
//                userRemoteMediator.load(
//                    LoadType.APPEND,
//                    pagingState,
//                )
//
//            // Then
//            assertTrue(result is MediatorResult.Success)
//            assertTrue(!(result as MediatorResult.Success).endOfPaginationReached)
//        }
//
//    @Test
//    fun `load APPEND returns Success with endOfPaginationReached true when remoteKey is null`() =
//        runTest {
//            // Given
//            val lastUserEntities = IntRange(1, 20).mockListUserResponse().map { it.toUserEntity() }
//
//            val pagingState =
//                PagingState<Int, UserEntity>(
//                    config = PagingConfig(pageSize = UserRemoteMediator.PAGE_SIZE),
//                    anchorPosition = 0,
//                    pages =
//                        listOf(
//                            Page<Int, UserEntity>(
//                                data = lastUserEntities,
//                                prevKey = null,
//                                nextKey = null,
//                            ),
//                        ),
//                    leadingPlaceholderCount = 0,
//                )
//
//            coEvery {
//                userRemoteKeyDao.getRemoteKeyByUserId(any())
//            } returns null
//
//            // When
//            val result =
//                userRemoteMediator.load(
//                    LoadType.APPEND,
//                    pagingState,
//                )
//
//            // Then
//            assertTrue(result is MediatorResult.Success)
//            assertTrue((result as MediatorResult.Success).endOfPaginationReached)
//        }
//
//    @Test
//    fun `load APPEND returns Success with endOfPaginationReached true when remoteKey nextKey is null`() =
//        runTest {
//            // Given
//            val lastUserEntities = IntRange(1, 20).mockListUserResponse().map { it.toUserEntity() }
//
//            val pagingState =
//                PagingState<Int, UserEntity>(
//                    config = PagingConfig(pageSize = UserRemoteMediator.PAGE_SIZE),
//                    anchorPosition = 0,
//                    pages =
//                        listOf(
//                            Page<Int, UserEntity>(
//                                data = lastUserEntities,
//                                prevKey = null,
//                                nextKey = null,
//                            ),
//                        ),
//                    leadingPlaceholderCount = 0,
//                )
//
//            val remoteKey =
//                UserRemoteKeyEntity(
//                    userId = lastUserEntities.last().id,
//                    prevKey = null,
//                    nextKey = null,
//                )
//
//            coEvery {
//                userRemoteKeyDao.getRemoteKeyByUserId(any())
//            } returns remoteKey
//
//            // When
//            val result =
//                userRemoteMediator.load(
//                    LoadType.APPEND,
//                    pagingState,
//                )
//
//            // Then
//            assertTrue(result is MediatorResult.Success)
//            assertTrue((result as MediatorResult.Success).endOfPaginationReached)
//        }
//
//    @Test
//    fun `append returns Success with endOfPaginationReached true`() =
//        runTest {
//            // Given
//            val lastUserEntities = IntRange(1, 20).mockListUserResponse().map { it.toUserEntity() }
//            val userResponses = (21..30).mockListUserResponse()
//            val lastRemoteKey =
//                UserRemoteKeyEntity(
//                    userId = lastUserEntities.last().id,
//                    prevKey = null,
//                    nextKey = lastUserEntities.last().id,
//                )
//            val pagingState =
//                PagingState<Int, UserEntity>(
//                    config = PagingConfig(pageSize = UserRemoteMediator.PAGE_SIZE),
//                    anchorPosition = 0,
//                    pages =
//                        listOf(
//                            Page<Int, UserEntity>(
//                                data = lastUserEntities,
//                                prevKey = null,
//                                nextKey = null,
//                            ),
//                        ),
//                    leadingPlaceholderCount = 0,
//                )
//
//            coEvery {
//                userServices.getUsers(
//                    lastUserEntities.last().id,
//                    UserRemoteMediator.PAGE_SIZE,
//                )
//            } returns userResponses
//
//            coEvery {
//                userRemoteKeyDao.getRemoteKeyByUserId(any())
//            } returns lastRemoteKey
//
//            val transactionLambda = slot<suspend () -> Unit>()
//            coEvery { database.withTransaction(capture(transactionLambda)) } coAnswers {
//                transactionLambda.captured.invoke()
//            }
//
//            // When
//            val result =
//                userRemoteMediator.load(
//                    LoadType.APPEND,
//                    pagingState,
//                )
//
//            // Then
//            assertTrue(result is MediatorResult.Success)
//            assertTrue((result as MediatorResult.Success).endOfPaginationReached)
//            coVerify { userDao.insertUsers(any()) }
//            coVerify { userRemoteKeyDao.insertAll(any()) }
//        }
//
//    @Test
//    fun `append returns Error`() =
//        runTest {
//            // Given
//            coEvery { userServices.getUsers(any(), any()) } throws Exception()
//
//            val lastUserEntities = IntRange(1, 20).mockListUserResponse().map { it.toUserEntity() }
//
//            val pagingState =
//                PagingState<Int, UserEntity>(
//                    config = PagingConfig(pageSize = UserRemoteMediator.PAGE_SIZE),
//                    anchorPosition = 0,
//                    pages =
//                        listOf(
//                            Page<Int, UserEntity>(
//                                data = lastUserEntities,
//                                prevKey = null,
//                                nextKey = null,
//                            ),
//                        ),
//                    leadingPlaceholderCount = 0,
//                )
//
//            val lastRemoteKey =
//                UserRemoteKeyEntity(
//                    userId = lastUserEntities.last().id,
//                    prevKey = null,
//                    nextKey = lastUserEntities.last().id,
//                )
//
//            coEvery {
//                userRemoteKeyDao.getRemoteKeyByUserId(any())
//            } returns lastRemoteKey
//
//            val transactionLambda = slot<suspend () -> Unit>()
//            coEvery { database.withTransaction(capture(transactionLambda)) } coAnswers {
//                transactionLambda.captured.invoke()
//            }
//
//            // When
//            val result =
//                userRemoteMediator.load(
//                    LoadType.APPEND,
//                    pagingState,
//                )
//
//            // Then
//            assertTrue(result is MediatorResult.Error)
//        }
//
//    @Test
//    fun `prepend return Success`() =
//        runTest {
//            // Given
//            val pagingState = createPagingState()
//
//            // When
//            val result =
//                userRemoteMediator.load(
//                    LoadType.PREPEND,
//                    pagingState,
//                )
//
//            // Then
//            assertTrue(result is MediatorResult.Success)
//            assertTrue((result as MediatorResult.Success).endOfPaginationReached)
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
//
//    private fun createPagingState(pages: List<Page<Int, UserEntity>> = listOf()): PagingState<Int, UserEntity> =
//        PagingState<Int, UserEntity>(
//            config = PagingConfig(pageSize = UserRemoteMediator.PAGE_SIZE),
//            anchorPosition = 0,
//            pages = pages,
//            leadingPlaceholderCount = 0,
//        )
// }
