package com.takehome.sauravrp.network.models

data class ResultsInfoDto(
    val count: Int,
    val pages: Int,
    val next: String?,
    val prev: String?
)
