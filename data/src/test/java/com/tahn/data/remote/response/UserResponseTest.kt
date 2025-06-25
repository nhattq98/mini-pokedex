// package com.tahn.data.remote.response
//
// import junit.framework.TestCase.assertEquals
// import org.junit.Test
//
// internal class UserResponseTest {
//    @Test
//    fun `toUserModel converts UserResponse to UserEntity`() {
//        // Given
//        val userResponse = UserResponse(
//            id = 1,
//            login = "username",
//            avatarUrl = "avatarUrl",
//            htmlUrl = "htmlUrl"
//        )
//
//        // When
//        val userEntity = userResponse.toUserEntity()
//
//        // Then
//        assertEquals(userEntity.id, userResponse.id)
//        assertEquals(userEntity.loginUsername, userResponse.login)
//        assertEquals(userEntity.htmlUrl, userResponse.htmlUrl)
//        assertEquals(userEntity.avatarUrl, userResponse.avatarUrl)
//    }
//
//    @Test
//    fun `toUserModel converts UserResponse with null values to UserEntity`() {
//        // Given
//        val userResponse = UserResponse(
//            id = null,
//            login = null,
//            avatarUrl = null,
//            htmlUrl = null
//        )
//
//        // When
//        val userEntity = userResponse.toUserEntity()
//
//        // Then
//        assertEquals(0, userEntity.id)
//        assertEquals("", userEntity.loginUsername)
//        assertEquals("", userEntity.htmlUrl)
//        assertEquals("", userEntity.avatarUrl)
//    }
// }
