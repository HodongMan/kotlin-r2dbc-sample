package com.alcoholic.application.request

import com.fasterxml.jackson.annotation.JsonRootName
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size


@JsonRootName("user")
class UserRegistRequest(
    @NotNull(message = "can't be missing")
    @Size(min = 1, message = "can't be empty")
    var userName: String?,
    @NotNull(message = "can't be missing")
    @Size(min = 1, message = "can't be empty")
    var email: String?,
    @NotNull(message = "can't be missing")
    @Size(min = 1, message = "can't be empty") 
    var password: String?
) {

}