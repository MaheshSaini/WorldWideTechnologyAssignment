package com.backbase.assignment.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.backbase.assignment.ui.domain.repository.Repository


class MovieBannerDetailsViewModel(val repository: Repository) : ViewModel() {

    fun setProgressBar(progress: Int) {
        if (progress < 50) {
            System.out.println("Yellow")
        } else {
            System.out.println("Green")
        }
    }
}