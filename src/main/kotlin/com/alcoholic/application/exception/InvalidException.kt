package com.alcoholic.application.exception

import org.springframework.validation.Errors

data class InvalidException(val erros: Errors?): RuntimeException()

object InvalidRequest {
    fun check(errors: Errors) {
        if (errors.hasFieldErrors())
            throw InvalidException(errors)
    }
}