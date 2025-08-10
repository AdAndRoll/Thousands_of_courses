package ru.vasilev.thousands_of_courses.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import ru.vasilev.domain.usecases.LoginUseCase
import ru.vasilev.domain.usecases.ValidateEmailUseCase
import ru.vasilev.thousands_of_courses.presentation.util.SingleEventFlow
import kotlinx.coroutines.flow.asSharedFlow
import android.util.Log

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val validateEmailUseCase: ValidateEmailUseCase
) : ViewModel() {

    private val _email = MutableStateFlow("")
    val email = _email.asStateFlow()

    private val _password = MutableStateFlow("")
    val password = _password.asStateFlow()

    // Событие для навигации на главный экран
    private val _navigateToMainScreen = SingleEventFlow<Unit>()
    val navigateToMainScreen = _navigateToMainScreen.asSharedFlow()

    // События для навигации в браузер
    private val _navigateToVk = SingleEventFlow<Unit>()
    val navigateToVk = _navigateToVk.asSharedFlow()

    private val _navigateToOk = SingleEventFlow<Unit>()
    val navigateToOk = _navigateToOk.asSharedFlow()

    // Состояние активности кнопки входа
    val isLoginButtonEnabled = combine(_email, _password) { email, password ->
        val isValid = validateEmailUseCase(email) && password.isNotBlank()
        Log.d("LoginViewModel", "Кнопка активна: $isValid (email: ${validateEmailUseCase(email)}, password: ${password.isNotBlank()})")
        isValid
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = false
    )

    fun onEmailChanged(newEmail: String) {
        _email.value = newEmail
        Log.d("LoginViewModel", "Email изменен на: $newEmail")
    }

    fun onPasswordChanged(newPassword: String) {
        _password.value = newPassword
        Log.d("LoginViewModel", "Пароль изменен")
    }

    fun onLoginClicked() {
        Log.d("LoginViewModel", "Кнопка входа была нажата. Email: ${email.value}, Пароль: <скрыт>")
        viewModelScope.launch {
            // Временное изменение: симулируем успешный вход, так как API пока не готов.
            // Когда API будет готов, вы сможете раскомментировать вызов loginUseCase.
            delay(1000) // Имитируем задержку сети
            Log.d("LoginViewModel", "Вход успешно симулирован. Переходим на главный экран.")
            _navigateToMainScreen.trySend(Unit)

            /*
            // Оригинальный код для реального API:
            val result = loginUseCase(email.value, password.value)
            result.onSuccess {
                Log.d("LoginViewModel", "Вход успешен. Переходим на главный экран.")
                _navigateToMainScreen.trySend(Unit)
            }.onFailure {
                Log.e("LoginViewModel", "Ошибка входа: ${it.message}")
            }
            */
        }
    }

    fun onVkClicked() {
        Log.d("LoginViewModel", "Кнопка VK нажата.")
        _navigateToVk.trySend(Unit)
    }

    fun onOkClicked() {
        Log.d("LoginViewModel", "Кнопка OK нажата.")
        _navigateToOk.trySend(Unit)
    }
}
