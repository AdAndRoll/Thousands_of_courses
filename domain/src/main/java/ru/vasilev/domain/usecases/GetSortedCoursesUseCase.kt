package ru.vasilev.domain.usecases

import ru.vasilev.domain.model.Course
import ru.vasilev.domain.repository.CourseRepository


/**
 * Use case для получения отсортированного списка курсов.
 * @param courseRepository Репозиторий для доступа к данным курсов.
 */
class GetSortedCoursesUseCase constructor(private val courseRepository: CourseRepository) {

    /**
     * Вызывает use case для получения списка курсов с учетом избранных и сортировки.
     * @param isDescending Флаг, указывающий, нужно ли сортировать по убыванию.
     * @param showOnlyFavorites Флаг, указывающий, нужно ли отображать только избранные курсы.
     * @return Список курсов, отсортированный по дате публикации.
     */
    suspend operator fun invoke(isDescending: Boolean, showOnlyFavorites: Boolean): List<Course> {
        val coursesWithStatus = if (showOnlyFavorites) {
            val favorites = courseRepository.getFavoriteCourses()
            favorites.map { it.copy(hasLike = true) }
        } else {

            val allCourses = courseRepository.getCourses()
            val favorites = courseRepository.getFavoriteCourses()
            val favoriteIds = favorites.map { it.id }.toSet()

            allCourses.map { course ->
                course.copy(hasLike = favoriteIds.contains(course.id))
            }
        }

        return if (isDescending) {
            coursesWithStatus.sortedByDescending { it.publishDate }
        } else {
            coursesWithStatus.sortedBy { it.publishDate }
        }
    }
}
