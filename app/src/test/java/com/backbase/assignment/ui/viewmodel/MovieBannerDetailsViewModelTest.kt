package com.backbase.assignment.ui.viewmodel

import junit.framework.TestCase
import org.junit.Assert
import org.junit.Assert.assertThat
import org.junit.Test

class MovieBannerDetailsViewModelTest {
    private lateinit var model: MovieBannerDetailsViewModel

    @Test
    fun testSetProgressBar() {
        model.setProgressBar(50)
        val result = model.setProgressBar(50)
        // assertThat(result).isEqualTo("50")
    }
}