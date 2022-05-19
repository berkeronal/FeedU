package com.berker.feedu.util

import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withTimeoutOrNull
import kotlin.coroutines.Continuation



suspend inline fun <T> suspendCoroutineWithTimeout(
    timeout: Long,
    crossinline block: (Continuation<T>) -> Unit
): T? {
    var finalValue: T? = null
    withTimeoutOrNull(timeout) {
        finalValue = suspendCancellableCoroutine(block = block)
    }
    return finalValue
}
