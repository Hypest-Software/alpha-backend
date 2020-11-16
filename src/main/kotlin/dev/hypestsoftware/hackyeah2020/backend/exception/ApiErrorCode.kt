package dev.hypestsoftware.hackyeah2020.backend.exception;

enum class ApiErrorCode : ErrorCode {
    AU_0001 // passwords don't match while trying to change password
    ;

    override fun id(): String = this.name
    override fun module(): String = "AUTH"
    override fun throwException(): Nothing {
        TODO("Not yet implemented")
    }
}