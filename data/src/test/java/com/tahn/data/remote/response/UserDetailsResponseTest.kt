// package com.tahn.data.remote.response
//
// import junit.framework.TestCase.assertEquals
// import org.junit.Test
//
// internal class UserDetailsResponseTest {
//
//    @Test
//    fun `toUserDetailsModel converts UserDetailsResponse to UserDetailsModel`() {
//        // Given
//        val userDetailsResponse = UserDetailsResponse(
//            id = 1,
//            login = "username",
//            avatarUrl = "avatarUrl",
//            blog = "Bio",
//            htmlUrl = "htmlUrl",
//            location = "Vietnam",
//            followers = 0,
//            following = 0,
//        )
//
//        // When
//        val userDetailsModel = userDetailsResponse.toUserDetailsModel()
//
//        // Then
//        assertEquals(userDetailsModel.id, userDetailsResponse.id)
//        assertEquals(userDetailsModel.username, userDetailsResponse.login)
//        assertEquals(userDetailsModel.avatarUrl, userDetailsResponse.avatarUrl)
//        assertEquals(userDetailsModel.blog, userDetailsResponse.blog)
//        assertEquals(userDetailsModel.htmlUrl, userDetailsResponse.htmlUrl)
//        assertEquals(userDetailsModel.location, userDetailsResponse.location)
//        assertEquals(userDetailsModel.followers, userDetailsResponse.followers)
//        assertEquals(userDetailsModel.followings, userDetailsResponse.following)
//    }
//
//    @Test
//    fun `toUserDetailsModel converts UserDetailsResponse with null values to UserDetailsModel`() {
//        // Given
//        val userDetailsResponse = UserDetailsResponse(
//            id = null,
//            login = null,
//            avatarUrl = null,
//            blog = null,
//            htmlUrl = null,
//            location = null,
//            followers = null,
//            following = null,
//        )
//
//        // When
//        val userDetailsModel = userDetailsResponse.toUserDetailsModel()
//
//        // Then
//        assertEquals(0, userDetailsModel.id)
//        assertEquals("", userDetailsModel.username)
//        assertEquals("", userDetailsModel.avatarUrl)
//        assertEquals("", userDetailsModel.blog)
//        assertEquals("", userDetailsModel.htmlUrl)
//        assertEquals("", userDetailsModel.location)
//        assertEquals(0, userDetailsModel.followers)
//        assertEquals(0, userDetailsModel.followings)
//    }
// }
