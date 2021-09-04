package com.alcoholic.application.service

import java.util.*
import reactor.core.publisher.Mono

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.mindrot.jbcrypt.BCrypt

import org.springframework.stereotype.Service
import org.springframework.beans.factory.annotation.Value

import com.alcoholic.application.model.User
import com.alcoholic.application.request.LoginRequest
import com.alcoholic.application.repository.UserRepository
import com.alcoholic.application.exception.InvalidLoginException


@Service
class UserService(val userRepository: UserRepository,
                  @Value("\${jwt.secret}") val jwtSecret: String,
                  @Value("\${jwt.issuer}") val jwtIssuer: String) {
    
    val currentUser = ThreadLocal<User>()

    fun makeNewToken(user: User): String {
        return Jwts.builder()
                .setIssuedAt(Date())
                .setSubject(user.email)
                .setIssuer(jwtIssuer)
                .setExpiration(Date(System.currentTimeMillis() + 10 * 24 * 60 * 60 * 1000)) // 10 days
                .signWith(SignatureAlgorithm.HS256, jwtSecret).compact()
    }

    fun validToken(token: String, user: User): Boolean {
        val claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).body
        
        return claims.subject == user.email && claims.issuer == jwtIssuer && Date().before(claims.expiration)
    }

    fun updateToken(user: User): Mono<User> {

        val saveUser: Mono<User> = userRepository.save(user);

        user.token = makeNewToken(user)

        return userRepository.save(user)
    }

    fun login(login: LoginRequest): Mono<User> {
        userRepository.findByEmail(login.email!!)?.let {
            if (BCrypt.checkpw(login.password!!, it.password)) {
                return updateToken(it)
            }

            throw InvalidLoginException("password", "invalid password")
        }

        throw InvalidLoginException("email", "unknown email")
    }

}