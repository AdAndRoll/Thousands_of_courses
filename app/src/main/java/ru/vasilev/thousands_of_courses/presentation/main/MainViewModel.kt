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
 * Управляет состоянием загрузки данных, сортировкой курсов
 * и отображением избранных курсов. Использует локальное кеширование.
 */
@HiltViewModel
class MainViewModel @Inject constructor(
    private val getSortedCoursesUseCase: GetSortedCoursesUseCase,
    private val updateFavoriteStatusUseCase: UpdateFavoriteStatusUseCase
) : ViewModel() {

    private val _allCourses = MutableStateFlow<List<Course>>(emptyList())

    private val _courses = MutableStateFlow<List<Course>>(emptyList())
    val courses: StateFlow<List<Course>> = _courses

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _isShowingFavorites = MutableStateFlow(false)
    val isShowingFavorites: StateFlow<Boolean> = _isShowingFavorites

    private var isSortedByDateDescending = true

    init {
        loadAllCourses()
    }

    /**
     * Загружает все курсы один раз.
     */
    private fun loadAllCourses() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                _allCourses.value = getSortedCoursesUseCase(isSortedByDateDescending, false)
                updateCourseList()
            } catch (e: Exception) {

            } finally {
                _isLoading.value = false
            }
        }
    }

    /**
     * Обновляет список курсов для UI, применяя фильтрацию и сортировку к локальному списку.
     */
    private fun updateCourseList() {
        val sortedList = if (isSortedByDateDescending) {
            _allCourses.value.sortedByDescending { it.publishDate }
        } else {
            _allCourses.value.sortedBy { it.publishDate }
        }

        val filteredList = if (_isShowingFavorites.value) {
            sortedList.filter { it.hasLike }
        } else {
            sortedList
        }
        _courses.value = filteredList
    }

    /**
     * Переключает порядок сортировки и обновляет список курсов.
     */
    fun toggleSort() {
        isSortedByDateDescending = !isSortedByDateDescending
        updateCourseList()
    }

    /**
     * Переключает режим отображения: все курсы или только избранные.
     * Обновляет список курсов.
     */
    fun toggleFavoritesMode() {
        _isShowingFavorites.value = !_isShowingFavorites.value
        updateCourseList()
    }

    /**
     * Обновляет статус избранного у курса.
     * Обновляет как базу данных, так и локальный список.
     */
    fun onFavoriteClicked(course: Course) {
        viewModelScope.launch {
            val updatedStatus = !course.hasLike
            updateFavoriteStatusUseCase(course.id, updatedStatus)

            _allCourses.value = _allCourses.value.map {
                if (it.id == course.id) {
                    it.copy(hasLike = updatedStatus)
                } else {
                    it
                }
            }
            updateCourseList()
        }
    }
}
