// package com.tahn.data.remote.retrofit.helper
//
// import com.tahn.data.helper.SecretHelper
// import com.tahn.data.remote.retrofit.interceptor.HeaderInterceptor
// import io.mockk.every
// import io.mockk.mockk
// import kotlinx.coroutines.test.runTest
// import okhttp3.OkHttpClient
// import okhttp3.mockwebserver.MockResponse
// import okhttp3.mockwebserver.MockWebServer
// import org.junit.Before
// import org.junit.Test
// import retrofit2.Retrofit
// import retrofit2.converter.gson.GsonConverterFactory
//
// internal class HeaderInterceptorTest {
//    private lateinit var mockWebServer: MockWebServer
//    private lateinit var okHttpClient: OkHttpClient
//    private lateinit var userService: UserServices
//    private lateinit var secretHelper: SecretHelper
//    private lateinit var headerInterceptor: HeaderInterceptor
//
//    @Before
//    fun setup() {
//        mockWebServer = MockWebServer()
//
//        secretHelper = mockk()
//        headerInterceptor = HeaderInterceptor(secretHelper)
//
//        okHttpClient =
//            OkHttpClient
//                .Builder()
//                .addInterceptor(headerInterceptor)
//                .build()
//
//        userService =
//            Retrofit
//                .Builder()
//                .baseUrl(mockWebServer.url("/"))
//                .addConverterFactory(GsonConverterFactory.create())
//                .client(okHttpClient)
//                .build()
//                .create(UserServices::class.java)
//    }
//
//    @Test
//    fun `intercept request should have authorization and content type header`() =
//        runTest {
//            // Given
//            val accessToken = "Test"
//            val expectedAuthorizationHeader = "Bearer $accessToken"
//            val expectedContentTypeHeader = "application/json"
//            val mockResponse =
//                MockResponse().setBody(
//                    """
//                    {
//                        "login": "user",
//                        "id": 123
//                    }
//                    """.trimIndent(),
//                )
//            every { secretHelper.getAccessToken() } returns accessToken
//
//            mockWebServer.enqueue(mockResponse)
//            userService.getUserDetails("test")
//            // When
//            val response = mockWebServer.takeRequest()
//
//            // Then
//            assert(response.getHeader("Authorization") == expectedAuthorizationHeader)
//            assert(response.getHeader("Content-Type") == expectedContentTypeHeader)
//        }
// }
