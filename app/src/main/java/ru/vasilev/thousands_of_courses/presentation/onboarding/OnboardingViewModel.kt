package ru.vasilev.thousands_of_courses.presentation.onboarding

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor() : ViewModel() {

    private val _navigateToLogin = Channel<Unit>(Channel.BUFFERED)
    val navigateToLogin = _navigateToLogin.receiveAsFlow()

    fun onNavigateToLogin() {
        _navigateToLogin.trySend(Unit)
    }
}