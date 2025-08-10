package ru.vasilev.thousands_of_courses.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.vasilev.domain.model.Course
import ru.vasilev.domain.usecases.GetSortedCoursesUseCase
import ru.vasilev.domain.usecases.UpdateFavoriteStatusUseCase
import javax.inject.Inject

/**
 * ViewModel для главного экрана.
 * Управляет состоянием загрузки данных и сортировкой курсов.
 */
@HiltViewModel
class MainViewModel @Inject constructor(
    private val getSortedCoursesUseCase: GetSortedCoursesUseCase,
    private val updateFavoriteStatusUseCase: UpdateFavoriteStatusUseCase
) : ViewModel() {

    private val _courses = MutableStateFlow<List<Course>>(emptyList())
    val courses: StateFlow<List<Course>> = _courses

    // Состояние загрузки данных
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    // Флаг для отслеживания текущего порядка сортировки
    // По умолчанию сортировка по убыванию (true = descending, false = ascending)
    private var isSortedByDateDescending = true

    init {
        loadCourses()
    }

    /**
     * Загружает курсы.
     * Отправляет флаг сортировки в UseCase для получения отсортированного списка.
     */
    fun loadCourses() {
        viewModelScope.launch {
            _isLoading.value = true // Показываем индикатор загрузки
            try {
                // Вызываем use case с параметром сортировки
                val sortedCourses = getSortedCoursesUseCase(isSortedByDateDescending)
                _courses.value = sortedCourses
            } catch (e: Exception) {
                // Обработка ошибок
                // Log.e("MainViewModel", "Error loading courses", e)
            } finally {
                _isLoading.value = false // Скрываем индикатор загрузки
            }
        }
    }

    /**
     * Переключает порядок сортировки и перезагружает курсы.
     */
    fun toggleSort() {
        isSortedByDateDescending = !isSortedByDateDescending
        loadCourses()
    }

    fun onFavoriteClicked(course: Course) {
        viewModelScope.launch {
            val updatedStatus = !course.hasLike
            updateFavoriteStatusUseCase(course.id, updatedStatus)
            loadCourses()
        }
    }
}
