package dev.hypestsoftware.hackyeah2020.backend.exception

enum class ApiErrorCode : ErrorCode {
    AU_0000, // generic exception, TODO split into multiple
    AU_0001, // passwords don't match while trying to change password
    AU_0002, // exception while updating user
    AU_0003, // wrong JWT provided by client
    AU_0004 // exception while getting currently logged in user
    ;

    override fun id(): String = this.name
    override fun module(): String = "AUTH"
    override fun throwException(): Nothing {
        TODO("Not yet implemented")
    }
}
