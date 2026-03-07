package com.itau.challenge.services

import com.itau.challenge.models.Transaction
import org.springframework.stereotype.Service
import java.time.OffsetDateTime
import com.itau.challenge.exceptions.UnprocessableEntityException
import java.util.concurrent.CopyOnWriteArrayList
import org.slf4j.LoggerFactory


@Service
class TransactionService {

    private val log = LoggerFactory.getLogger(TransactionService::class.java)

    private val transactions = CopyOnWriteArrayList<Transaction>()

    fun addTransaction(transaction: Transaction) {


        log.info("Receive transaction try: value={}, date={}", transaction.amount, transaction.date)

        val currentTime = OffsetDateTime.now()

        if (transaction.date.isAfter(currentTime)) {
            log.warn("Transaction Failed: Future date detected.")
            throw UnprocessableEntityException("Transaction date cannot be in the future")
        }

        if (transaction.amount < 0.0) {
            log.warn("Transaction Failed: negative value.")
            throw UnprocessableEntityException("Transaction amount must be greater than 0")
        }

        transactions.add(transaction)
        log.info("Transaction added successfully! Memory: {}", transactions.size)
    }

    fun clearTransactions() {
        log.info("Clearing all transactions from memory. Current count: {}", transactions.size)
        transactions.clear()
        log.info("Memory cleared. Current transaction count: {}", transactions.size)
    }

    fun getAllTransactions(): List<Transaction> {
        return transactions
    }
}