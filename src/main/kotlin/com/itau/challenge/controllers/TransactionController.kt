package com.itau.challenge.controllers

import com.itau.challenge.models.Transaction
import com.itau.challenge.services.TransactionService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.DeleteMapping


@RestController
@RequestMapping("/transacao")
class TransactionController(
    private val transactionService: TransactionService
) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createTransaction(@RequestBody request: Transaction) {
        transactionService.addTransaction(request)
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    fun deleteTransactions() {
        transactionService.clearTransactions()
    }
}