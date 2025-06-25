// package com.tahn.domain.usecase
//
// import androidx.paging.PagingData
// import io.mockk.every
// import io.mockk.mockk
// import kotlinx.coroutines.flow.flowOf
// import kotlinx.coroutines.test.runTest
// import org.junit.Before
// import org.junit.Test
//
// internal class GetUserPagingSourceUseCaseTest {
//    private lateinit var userRepository: UserRepository
//    private lateinit var getUserPagingSourceUseCase: GetUserPagingSourceUseCase
//
//    @Before
//    fun setup() {
//        userRepository = mockk()
//        getUserPagingSourceUseCase = GetUserPagingSourceUseCase(userRepository)
//    }
//
//    @Test
//    fun `invoke should return user paging source`() {
//        // Given
//        val userModelList = mockk<List<UserModel>>()
//        val userPagingData = PagingData.from(userModelList)
//
//        every { userRepository.getUserPaging() } returns flowOf(userPagingData)
//
//        // When
//        val result = getUserPagingSourceUseCase()
//
//        // Then
//        runTest {
//            result.collect {
//                assert(it == userPagingData)
//            }
//        }
//    }
// }
