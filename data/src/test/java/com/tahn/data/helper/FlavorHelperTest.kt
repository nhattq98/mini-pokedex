package com.tahn.data.helper

import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertTrue
import org.junit.Test

internal class FlavorHelperTest {
    @Test
    fun `isDevMode returns true when flavor is dev`() {
        val mockProvider = mockk<FlavorProvider>()
        every { mockProvider.getFlavor() } returns "dev"
        val helper = FlavorHelper(mockProvider)

        val result = helper.isDevMode()

        assertTrue(result)
    }

    @Test
    fun `isProdMode returns true when flavor is prod`() {
        val mockProvider = mockk<FlavorProvider>()
        every { mockProvider.getFlavor() } returns "prod"
        val helper = FlavorHelper(mockProvider)

        val result = helper.isProdMode()

        assertTrue(result)
    }
}
