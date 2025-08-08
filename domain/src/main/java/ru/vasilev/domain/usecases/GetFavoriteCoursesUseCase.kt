package ru.vasilev.domain.usecases

import ru.vasilev.domain.model.Course
import ru.vasilev.domain.repository.CourseRepository

class GetFavoriteCoursesUseCase(private val courseRepository: CourseRepository) {
    suspend operator fun invoke(): List<Course> {
        return courseRepository.getFavoriteCourses()
    }
}