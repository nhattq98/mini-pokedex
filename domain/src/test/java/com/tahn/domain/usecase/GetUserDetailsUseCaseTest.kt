// package com.tahn.domain.usecase
//
// import io.mockk.every
// import io.mockk.mockk
// import kotlinx.coroutines.flow.flowOf
// import kotlinx.coroutines.test.runTest
// import org.junit.Before
// import org.junit.Test
//
// internal class GetUserDetailsUseCaseTest {
//    private lateinit var userRepository: UserRepository
//    private lateinit var getUserDetailsUseCase: GetUserDetailsUseCase
//
//    @Before
//    fun setup() {
//        userRepository = mockk()
//        getUserDetailsUseCase = GetUserDetailsUseCase(userRepository)
//    }
//
//    @Test
//    fun `invoke should return user details`() {
//        // Given
//        val username = "testuser"
//        val userDetails = mockk<UserDetailsModel>()
//
//        every { userRepository.getUserDetails(username) } returns flowOf(userDetails)
//
//        // When
//        val result = getUserDetailsUseCase(username)
//
//        // Then
//        runTest {
//            result.collect {
//                assert(it == userDetails)
//            }
//        }
//    }
// }
