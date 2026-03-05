package com.itau.challenge.controllers

import com.itau.challenge.services.StatisticService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import org.springframework.http.HttpStatus


@RestController
@RequestMapping("estatistica")
class StatisticController(
    private val statisticService: StatisticService
) {
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    fun getStatistics() = statisticService.getStatistic()
}