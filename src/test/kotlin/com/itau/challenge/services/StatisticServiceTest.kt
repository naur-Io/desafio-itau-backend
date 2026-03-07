package com.itau.challenge.services

import com.itau.challenge.models.Transaction
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.time.OffsetDateTime

class StatisticServiceTest {

    private val transactionService = TransactionService()
    private val statisticService = StatisticService(transactionService)

    @BeforeEach
    fun setUp() {
        transactionService.clearTransactions()
    }

    @Test
    fun shouldreturnstatiscticsEmptyWhenDontHaveTransactions() {
        val stats = statisticService.getStatistic()

        assertEquals(0L, stats.count)
        assertEquals(0.0, stats.sum)
        assertEquals(0.0, stats.max)
        assertEquals(0.0, stats.min)
        assertEquals(0.0, stats.avg)
    }

    @Test
    fun shouldcalculateStatisticsForLast60Seconds() {
        transactionService.addTransaction(Transaction(100.0, OffsetDateTime.now()))
        transactionService.addTransaction(Transaction(50.0, OffsetDateTime.now().minusSeconds(30)))
        transactionService.addTransaction(Transaction(1000.0, OffsetDateTime.now().minusMinutes(2)))

        val stats = statisticService.getStatistic()

        assertEquals(2L, stats.count)
        assertEquals(150.0, stats.sum)
        assertEquals(100.0, stats.max)
        assertEquals(50.0, stats.min)
        assertEquals(75.0, stats.avg)
    }
}