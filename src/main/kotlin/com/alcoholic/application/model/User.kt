package com.alcoholic.application.model

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonRootName

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime


@Table("users")
@JsonRootName("user")
data class User(@Id
                var id: Long = 0,
                var email: String = "",
                @JsonIgnore
                var password: String = "",
                var username: String = "",
                var token: String = "",
                var createdAt: LocalDateTime = LocalDateTime.now(),
                var updatedAt: LocalDateTime = createdAt) {

    override fun toString(): String = ""
}