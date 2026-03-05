package com.itau.challenge.models

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.OffsetDateTime

data class Transaction (

    @JsonProperty("valor")
    val amount: Double,

    @JsonProperty("dataHora")
    val date: OffsetDateTime
)


