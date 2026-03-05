package com.itau.challenge.services

import com.itau.challenge.models.Statistic
import com.itau.challenge.models.Transaction
import org.springframework.stereotype.Service
import java.time.OffsetDateTime
import java.util.concurrent.CopyOnWriteArrayList


@Service
class StatisticService(
    private val transactionService: TransactionService
) {
    fun getStatistic(): Statistic {
        val currentTime = OffsetDateTime.now().minusSeconds(60)

        val recentTransactions = transactionService.getAllTransactions().filter { it.date.isAfter(currentTime) }

        val stats = recentTransactions.stream().mapToDouble { it.amount }.summaryStatistics()

        if (stats.count == 0L) {
            return Statistic(
                count = 0L,
                sum = 0.0,
                avg = 0.0,
                max = 0.0,
                min = 0.0
            )
        }

        return Statistic(
            count = stats.count,
            sum = stats.sum,
            avg = stats.average,
            max = stats.max,
            min = stats.min
        )
    }


}
