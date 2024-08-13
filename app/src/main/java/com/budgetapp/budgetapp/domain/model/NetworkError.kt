package com.budgetapp.budgetapp.domain.model

data class NetworkError(
    val error: ApiError,
    val t: Throwable? = null
)

//Object with a message:String
enum class ApiError(
    val message: String
) {
    NetworkError("Network Error"),
    UnknownResponse("Unknown Response"),
    UnknownError("Unknown Error")
}
