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

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getSortedCoursesUseCase: GetSortedCoursesUseCase,
    private val updateFavoriteStatusUseCase: UpdateFavoriteStatusUseCase
) : ViewModel() {

    private val _courses = MutableStateFlow<List<Course>>(emptyList())
    val courses: StateFlow<List<Course>> = _courses

    init {
        loadCourses()
    }

    fun loadCourses() {
        viewModelScope.launch {
            try {
                val sortedCourses = getSortedCoursesUseCase()
                _courses.value = sortedCourses
            } catch (e: Exception) {
                // Обработка ошибок, например, Log.e("MainViewModel", "Error loading courses", e)
            }
        }
    }

    fun onFavoriteClicked(course: Course) {
        viewModelScope.launch {
            val updatedStatus = !course.hasLike
            updateFavoriteStatusUseCase(course.id, updatedStatus)
            // После обновления статуса в репозитории, обновляем данные в UI
            loadCourses()
        }
    }
}