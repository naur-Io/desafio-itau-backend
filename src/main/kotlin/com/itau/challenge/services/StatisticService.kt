package com.itau.challenge.services

import com.itau.challenge.models.Statistic
import com.itau.challenge.models.Transaction
import org.springframework.stereotype.Service
import java.time.OffsetDateTime
import java.util.concurrent.CopyOnWriteArrayList
import org.slf4j.LoggerFactory


@Service
class StatisticService(
    private val transactionService: TransactionService

) {

    private val log = LoggerFactory.getLogger(StatisticService::class.java)

    fun getStatistic(): Statistic {
        log.info("Calculating statistics for transactions in the last 60 seconds.")
        val currentTime = OffsetDateTime.now().minusSeconds(60)

        val recentTransactions = transactionService.getAllTransactions().filter { it.date.isAfter(currentTime) }

        val stats = recentTransactions.stream().mapToDouble { it.amount }.summaryStatistics()

        if (stats.count == 0L) {
            log.info("None transactions found in the last 60 seconds. Returning default statistics.")
            return Statistic(
                count = 0L,
                sum = 0.0,
                avg = 0.0,
                max = 0.0,
                min = 0.0
            )
        }

        log.info("Statistics calculated successfully" )
        return Statistic(
            count = stats.count,
            sum = stats.sum,
            avg = stats.average,
            max = stats.max,
            min = stats.min
        )
    }


}
