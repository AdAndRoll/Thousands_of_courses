package ru.vasilev.domain.usecases

import ru.vasilev.domain.model.Course
import ru.vasilev.domain.repository.CourseRepository


/**
 * Use case для получения отсортированного списка курсов.
 * @param courseRepository Репозиторий для доступа к данным курсов.
 */
class GetSortedCoursesUseCase(private val courseRepository: CourseRepository) {

    /**
     * Вызывает use case для получения списка курсов с учетом избранных и сортировки.
     * @param isDescending Флаг, указывающий, нужно ли сортировать по убыванию.
     * @return Список курсов, отсортированный по дате публикации.
     */
    suspend operator fun invoke(isDescending: Boolean): List<Course> {
        val courses = courseRepository.getCourses()
        val favorites = courseRepository.getFavoriteCourses()
        val favoriteIds = favorites.map { it.id }.toSet()

        val coursesWithStatus = courses.map { course ->
            course.copy(hasLike = favoriteIds.contains(course.id))
        }

        // Сортировка по publishDate в зависимости от параметра isDescending
        return if (isDescending) {
            coursesWithStatus.sortedByDescending { it.publishDate }
        } else {
            coursesWithStatus.sortedBy { it.publishDate }
        }
    }
}
