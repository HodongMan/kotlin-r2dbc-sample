package com.alcoholic.application.repository

import org.springframework.data.repository.reactive.ReactiveCrudRepository
//import org.springframework.stereotype.Repository

import com.alcoholic.application.model.User


interface UserRepository : ReactiveCrudRepository<User, Long> {

}