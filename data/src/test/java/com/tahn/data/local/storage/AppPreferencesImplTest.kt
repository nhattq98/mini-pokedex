package com.tahn.data.local.storage

import android.content.Context
import android.content.SharedPreferences
import androidx.test.core.app.ApplicationProvider
import com.tahn.domain.provider.DispatcherProvider
import io.mockk.every
import io.mockk.mockk
import io.mockk.unmockkObject
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
@OptIn(ExperimentalCoroutinesApi::class)
internal class AppPreferencesImplTest {
    private lateinit var context: Context
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var dispatcherProvider: DispatcherProvider
    private lateinit var appPreferences: AppPreferencesImpl

    private val testKey = AppPreferenceKey.LONG_LAST_TIME_FETCH_USER

    @Before
    fun setUp() {
        context = ApplicationProvider.getApplicationContext()
        sharedPreferences = context.getSharedPreferences("test_prefs", Context.MODE_PRIVATE)
        dispatcherProvider = mockk()

        every { dispatcherProvider.io() } returns UnconfinedTestDispatcher()

        appPreferences =
            AppPreferencesImpl(context, dispatcherProvider)
    }

    @After
    fun tearDown() {
        unmockkObject(sharedPreferences)
    }

    @Test
    fun `Save and get non-encrypted Int value`() =
        runTest {
            // Given
            val value = 42
            appPreferences.saveValue(testKey, value)

            // When
            val result = appPreferences.getValue(testKey, value)

            // Then
            assertEquals(value, result)
        }

    @Test
    fun `getValue Int value should return default value if key is not found`() {
        // Given
        val expected = 42

        // When
        val result = appPreferences.getValue(testKey, expected)

        // Then
        assertEquals(expected, result)
    }

    @Test
    fun `Save and get non-encrypted Long value`() =
        runTest {
            // Given
            val value = 123L
            appPreferences.saveValue(testKey, value)

            // When
            val result = appPreferences.getValue(testKey, value)

            // Then
            assertEquals(value, result)
        }

    @Test
    fun `getValue Long value should return default value if key is not found`() {
        // Given
        val expected = 42L

        // When
        val result = appPreferences.getValue(testKey, expected)

        // Then
        assertEquals(expected, result)
    }

    @Test
    fun `Save and get non-encrypted Double value`() =
        runTest {
            // Given
            val value = 12.3
            appPreferences.saveValue(testKey, value)

            // When
            val result = appPreferences.getValue(testKey, value)

            // Then
            assertEquals(value, result)
        }

    @Test
    fun `getValue Double value should return default value if key is not found`() {
        // Given
        val expected = 42.2

        // When
        val result = appPreferences.getValue(testKey, expected)

        // Then
        assertEquals(expected, result)
    }

    @Test
    fun `Save and get non-encrypted Float value`() =
        runTest {
            // Given
            val value = 12.3f
            appPreferences.saveValue(testKey, value)

            // When
            val result = appPreferences.getValue(testKey, value)

            // Then
            assertEquals(value, result)
        }

    @Test
    fun `getValue Float value should return default value if key is not found`() {
        // Given
        val expected = 42.2f

        // When
        val result = appPreferences.getValue(testKey, expected)

        // Then
        assertEquals(expected, result)
    }

    @Test
    fun `Save and get non-encrypted Boolean value`() =
        runTest {
            // Given
            val value = true
            appPreferences.saveValue(testKey, value)

            // When
            val result = appPreferences.getValue(testKey, value)

            // Then
            assertEquals(value, result)
        }

    @Test
    fun `getValue Boolean value should return default value if key is not found`() {
        // Given
        val expected = true

        // When
        val result = appPreferences.getValue(testKey, expected)

        // Then
        assertEquals(expected, result)
    }

    @Test
    fun `Save and get non-encrypted String value`() =
        runTest {
            // Given
            val value = "hello"
            appPreferences.saveValue(testKey, value)

            // When
            val result = appPreferences.getValue(testKey, value)

            // Then
            assertEquals(value, result)
        }

    @Test
    fun `getValue String value should return default value if key is not found`() {
        // Given
        val expected = "hello"

        // When
        val result = appPreferences.getValue(testKey, expected)

        // Then
        assertEquals(expected, result)
    }

    @Test
    fun `Save and get non-encrypted StringSet value`() =
        runTest {
            // Given
            val value = setOf("a", "b")
            appPreferences.saveValue(testKey, value)

            // When
            val result = appPreferences.getValue(testKey, value)

            // Then
            assertEquals(value, result)
        }

    @Test
    fun `getValue StringSet value should return default value if key is not found`() {
        // Given
        val expected = setOf("a", "b")

        // When
        val result = appPreferences.getValue(testKey, expected)

        // Then
        assertEquals(expected, result)
    }

    @Test
    fun `Save and get non-encrypted Other value return default value`() =
        runTest {
            // Given
            data class DummyClass(
                val id: Int,
            )

            val value = DummyClass(1)
            appPreferences.saveValue(testKey, value)

            // When
            val result = appPreferences.getValue(testKey, DummyClass(2))

            // Then
            assertEquals(DummyClass(2), result)
        }

    @Test
    fun `Get non-encrypted value with missing key returns default`() {
        // When
        val result = appPreferences.getValue(testKey, "default")

        // Then
        assertEquals("default", result)
    }

    @Test
    fun `Catch exception during SharedPreferences read returns default`() {
        // When
        val result = appPreferences.getValue(testKey, "default")

        // Then
        assertEquals("default", result)
    }
}
