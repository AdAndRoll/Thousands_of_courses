package ru.vasilev.domain.usecases

import ru.vasilev.domain.model.Course
import ru.vasilev.domain.repository.CourseRepository

class GetSortedCoursesUseCase(private val courseRepository: CourseRepository) {
    suspend operator fun invoke(): List<Course> {
        val courses = courseRepository.getCourses()
        val favorites = courseRepository.getFavoriteCourses()
        val favoriteIds = favorites.map { it.id }.toSet()

        val coursesWithStatus = courses.map { course ->
            course.copy(hasLike = favoriteIds.contains(course.id))
        }

        // Сортировка по publishDate по убыванию
        return coursesWithStatus.sortedByDescending { it.publishDate }
    }
}