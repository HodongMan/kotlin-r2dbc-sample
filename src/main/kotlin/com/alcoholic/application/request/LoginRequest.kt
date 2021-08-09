package com.alcoholic.application.request

import com.fasterxml.jackson.annotation.JsonRootName
import javax.validation.constraints.NotNull
import javax.validation.constraints.Pattern
import javax.validation.constraints.Size


@JsonRootName("user")
class LoginRequest(
    @NotNull(message = "must have data")
    @Size(min = 5, message = "data length must be more than 5")
    @Pattern(
        regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}$",
        message = "must be email patterns"
    )
    var email: String?,
    @NotNull(message = "must have data")
    @Size(min = 1, message = "data length must be more than 1")
    var password: String?
) {

}