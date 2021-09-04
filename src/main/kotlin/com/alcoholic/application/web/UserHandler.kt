package com.alcoholic.application.web


import javax.validation.Valid

import reactor.core.publisher.Mono

import org.springframework.web.bind.annotation.*
import org.springframework.validation.BindException
import org.springframework.validation.Errors
import org.springframework.validation.FieldError

import com.alcoholic.application.exception.InvalidLoginException
import com.alcoholic.application.exception.InvalidException
import com.alcoholic.application.exception.InvalidRequest
import com.alcoholic.application.request.LoginRequest
import com.alcoholic.application.model.User;
import com.alcoholic.application.repository.UserRepository
import com.alcoholic.application.service.UserService

@RestController
class UserHandler(val repository: UserRepository,
                  val service: UserService) {

    @PostMapping
    fun login(@Valid @RequestBody login: LoginRequest, errors: Errors): Any {
        InvalidRequest.check(errors)

        try {
            service.login(login)?.let {
                return view(service.updateToken(it))
            }

            return ForbiddenRequestException()
        } catch (e: InvalidLoginException) {
            val loginErrors = BindException(this, "")
            loginErrors.addError(FieldError("", e.field, e.error))
            throw InvalidException(loginErrors)
        }
    }

    fun view(user: Mono<User>) = mapOf("user" to user)
}