// package com.tahn.data.local.database.dao
//
// import android.content.Context
// import androidx.room.Room
// import androidx.test.core.app.ApplicationProvider
// import com.tahn.data.local.database.AppDatabase
// import junit.framework.TestCase.assertEquals
// import junit.framework.TestCase.assertNotNull
// import junit.framework.TestCase.assertNull
// import kotlinx.coroutines.test.runTest
// import org.junit.After
// import org.junit.Before
// import org.junit.Test
// import org.junit.runner.RunWith
// import org.robolectric.RobolectricTestRunner
// import java.io.IOException
//
// @RunWith(RobolectricTestRunner::class)
// internal class UserRemoteKeyDaoTest {
//    private lateinit var roomDatabase: AppDatabase
//    private lateinit var userRemoteKeyDao: UserRemoteKeyDao
//
//    @Before
//    fun setup() {
//        roomDatabase =
//            Room
//                .inMemoryDatabaseBuilder(
//                    ApplicationProvider.getApplicationContext<Context>(),
//                    AppDatabase::class.java,
//                ).build()
//        userRemoteKeyDao = roomDatabase.userRemoteKeyDao()
//    }
//
//    @After
//    @Throws(IOException::class)
//    fun tearDown() {
//        roomDatabase.close()
//    }
//
//    @Test
//    @Throws(Exception::class)
//    fun `insertAll with nextKey is not null and prevKey is null`() =
//        runTest {
//            // Given
//            val userRemoteKeyEntities = (1..10).mockUserRemoteKeyEntities()
//
//            // When
//            userRemoteKeyDao.insertAll(userRemoteKeyEntities)
//
//            // Then
//            val result = getResult(userRemoteKeyEntities.first().userId)
//            assertEquals(result, userRemoteKeyEntities.first())
//            assertNull(userRemoteKeyEntities.first().prevKey)
//            assertNotNull(userRemoteKeyEntities.first().nextKey)
//        }
//
//    @Test
//    @Throws(Exception::class)
//    fun `insertAll with nextKey is null and prevKey is not null`() =
//        runTest {
//            // Given
//            val userRemoteKeyEntities =
//                (1..10).map {
//                    UserRemoteKeyEntity(
//                        userId = it,
//                        prevKey = it,
//                        nextKey = null,
//                    )
//                }
//
//            // When
//            userRemoteKeyDao.insertAll(userRemoteKeyEntities)
//
//            // Then
//            val result = getResult(userRemoteKeyEntities.first().userId)
//            assertEquals(result, userRemoteKeyEntities.first())
//            assertNull(userRemoteKeyEntities.first().nextKey)
//            assertNotNull(userRemoteKeyEntities.first().prevKey)
//        }
//
//    @Test
//    @Throws(Exception::class)
//    fun clearAll() =
//        runTest {
//            // Given
//            val firstList = (1..10).mockUserRemoteKeyEntities()
//            userRemoteKeyDao.insertAll(firstList)
//
//            // When
//            userRemoteKeyDao.clearAll()
//
//            // Then
//            val result = getResult(firstList.first().userId)
//            assert(result == null)
//        }
//
//    private suspend fun getResult(userId: Int): UserRemoteKeyEntity? = userRemoteKeyDao.getRemoteKeyByUserId(userId)
//
//    private fun IntRange.mockUserRemoteKeyEntities(): List<UserRemoteKeyEntity> =
//        this.map {
//            UserRemoteKeyEntity(
//                userId = it,
//                prevKey = null,
//                nextKey = it + 20,
//            )
//        }
// }
