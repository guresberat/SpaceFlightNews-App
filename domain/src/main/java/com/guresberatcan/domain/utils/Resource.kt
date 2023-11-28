package com.guresberatcan.domain.utils

/**
 * Sealed class representing the result of an operation that can be either success or error.
 *
 * @param data The data associated with the operation result.
 * @param errorMessage The error message associated with the operation result in case of an error.
 */
sealed class Resource<T>(
    val data: T? = null,
    val errorMessage: String? = null
) {
    /**
     * Represents a successful operation with associated data.
     *
     * @param data The data associated with the successful operation.
     */
    class Success<T>(data: T) : Resource<T>(data)

    /**
     * Represents an error in the operation with an optional error message and associated data.
     *
     * @param message The error message explaining the reason for the error.
     * @param data The data associated with the error (if applicable).
     */
    class Error<T>(message: String, data: T? = null) : Resource<T>(data, message)
}