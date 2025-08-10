package ru.vasilev.thousands_of_courses.presentation.util

import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow


class SingleEventFlow<T> {
    private val _events = MutableSharedFlow<T>(
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    fun asSharedFlow(): SharedFlow<T> = _events.asSharedFlow()

    fun trySend(value: T) {
        _events.tryEmit(value)
    }
}