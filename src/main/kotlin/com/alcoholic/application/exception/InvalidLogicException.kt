package com.alcoholic.application.exception

class InvalidLoginException(val field: String, val error: String) : RuntimeException()