package ru.vasilev.thousands_of_courses.presentation.util

import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow

/**
 * A utility class for creating a shared flow that emits events only once.
 * This is useful for one-time events like navigation, showing a Toast, etc.
 */
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