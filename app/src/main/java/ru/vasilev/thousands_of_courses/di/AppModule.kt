package ru.vasilev.thousands_of_courses.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.vasilev.data.repository.CoursesRepositoryImpl
import ru.vasilev.domain.repository.CourseRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    /**
     * Предоставляет реализацию CourseRepository.
     * Dagger Hilt теперь будет знать, что когда где-то запрашивается CourseRepository,
     * нужно предоставить экземпляр CourseRepositoryImpl.
     *
     * @param courseRepositoryImpl Конкретная реализация репозитория.
     * @return Интерфейс репозитория.
     */
    @Binds
    @Singleton
    abstract fun bindCourseRepository(courseRepositoryImpl: CoursesRepositoryImpl): CourseRepository
}