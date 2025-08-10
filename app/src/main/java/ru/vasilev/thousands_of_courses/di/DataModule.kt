package ru.vasilev.thousands_of_courses.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module(includes = [
    RemoteModule::class,
    DatabaseModule::class,
    RepositoryModule::class
])
@InstallIn(SingletonComponent::class)
object DataModule