package com.itau.challenge.services

import com.itau.challenge.exceptions.UnprocessableEntityException
import com.itau.challenge.models.Transaction
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.assertThrows
import java.time.OffsetDateTime
import kotlin.test.Test
import kotlin.test.assertEquals


class TransactionServiceTest {

    private val transactionService = TransactionService()

    @BeforeEach
    fun setUp() {
        transactionService.clearTransactions()
    }


    @Test
    fun shouldAddTransaction() {
        val validTransaction = Transaction(
            amount = 100.0,
            date = OffsetDateTime.now().minusSeconds(60)
        )

        transactionService.addTransaction(validTransaction)
        val savedTransaction = transactionService.getAllTransactions()
        assertEquals(1, savedTransaction.size)
        assertEquals(100.0, savedTransaction[0].amount)
    }


    @Test
    fun shouldThrowInFutureTransaction() {
        val transacaoFutura = Transaction(
            amount = 100.0,
            date = OffsetDateTime.now().plusDays(1)
        )

        assertThrows<UnprocessableEntityException> {
            transactionService.addTransaction(transacaoFutura)
        }
    }

    @Test
    fun shouldThrowWhenValueMinorOfZero(){
        val lowerTransaction = Transaction(
            amount = -1.0,
            date = OffsetDateTime.now().minusSeconds(60)
        )

        assertThrows<UnprocessableEntityException> {
            transactionService.addTransaction(lowerTransaction)
        }
    }
}