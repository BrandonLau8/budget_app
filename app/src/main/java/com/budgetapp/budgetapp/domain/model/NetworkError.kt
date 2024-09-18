package com.budgetapp.budgetapp.domain.model

data class NetworkError(
    val error: ApiError,
    val t: Throwable? = null //This property allows you to optionally include the original throwable that caused the error.
)

//enums are used to define a set of named constants
enum class ApiError(
    //Each enum constant has an associated message property of type String. This provides a human-readable description of the error type
    val message: String
) {
    NetworkError("Network Error"),
    UnknownResponse("Unknown Response"),
    UnknownError("Unknown Error")
}
