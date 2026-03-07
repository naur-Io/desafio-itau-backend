package com.itau.challenge.exceptions

import org.springframework.http.HttpMessage
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice


@RestControllerAdvice
class GlobalExceptionHandler {

    // Captura exceção de regra de negócio Data no futuro ou valor negativo
    @ExceptionHandler(UnprocessableEntityException::class)
    fun handleUnprocessableEntity(ex: UnprocessableEntityException): ResponseEntity<Void> {
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build()
    }

    @ExceptionHandler(HttpMessageNotReadableException::class)
    fun handleBadRequest(ex: HttpMessageNotReadableException): ResponseEntity<Void> {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build()
    }

    @ExceptionHandler(Exception::class)
    fun handleGenericException(ex: Exception): ResponseEntity<Void> {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build()

    }
}