package ru.vasilev.domain.usecases

import ru.vasilev.domain.repository.CourseRepository

class UpdateFavoriteStatusUseCase(private val courseRepository: CourseRepository) {
    suspend operator fun invoke(courseId: String, isFavorite: Boolean) {
        if (isFavorite) {
            courseRepository.addFavorite(courseId)
        } else {
            courseRepository.removeFavorite(courseId)
        }
    }
}   