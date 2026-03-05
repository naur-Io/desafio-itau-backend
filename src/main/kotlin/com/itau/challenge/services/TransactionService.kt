package com.itau.challenge.services

import com.itau.challenge.models.Transaction
import org.springframework.stereotype.Service
import java.time.OffsetDateTime
import com.itau.challenge.exceptions.UnprocessableEntityException
import java.util.concurrent.CopyOnWriteArrayList


@Service
class TransactionService {

    private val transactions = CopyOnWriteArrayList<Transaction>()

    fun addTransaction(transaction: Transaction) {

        val currentTime = OffsetDateTime.now()

        if (transaction.date.isAfter(currentTime)) {
            throw UnprocessableEntityException("Transaction date cannot be in the future")
        }

        if (transaction.amount < 0.0) {
            throw UnprocessableEntityException("Transaction amount must be greater than 0")
        }

        transactions.add(transaction)
    }

    fun clearTransactions() {
        transactions.clear()
    }

    fun getAllTransactions(): List<Transaction> {
        return transactions
    }
}