package ru.vasilev.thousands_of_courses.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.vasilev.domain.repository.AuthRepository
import ru.vasilev.domain.repository.CourseRepository
import ru.vasilev.domain.usecases.GetFavoriteCoursesUseCase
import ru.vasilev.domain.usecases.GetSortedCoursesUseCase
import ru.vasilev.domain.usecases.LoginUseCase
import ru.vasilev.domain.usecases.UpdateFavoriteStatusUseCase
import ru.vasilev.domain.usecases.ValidateEmailUseCase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DomainModule {

    @Provides
    @Singleton
    fun provideGetFavoriteCoursesUseCase(courseRepository: CourseRepository): GetFavoriteCoursesUseCase {
        return GetFavoriteCoursesUseCase(courseRepository)
    }

    @Provides
    @Singleton
    fun provideGetSortedCoursesUseCase(courseRepository: CourseRepository): GetSortedCoursesUseCase {
        return GetSortedCoursesUseCase(courseRepository)
    }

    @Provides
    @Singleton
    fun provideLoginUseCase(authRepository: AuthRepository): LoginUseCase {
        return LoginUseCase(authRepository)
    }

    @Provides
    @Singleton
    fun provideUpdateFavoriteStatusUseCase(courseRepository: CourseRepository): UpdateFavoriteStatusUseCase {
        return UpdateFavoriteStatusUseCase(courseRepository)
    }

    @Provides
    @Singleton
    fun provideValidateEmailUseCase(): ValidateEmailUseCase {
        return ValidateEmailUseCase()
    }
}