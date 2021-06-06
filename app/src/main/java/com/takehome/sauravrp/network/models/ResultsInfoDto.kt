package com.takehome.sauravrp.network.models

data class ResultsInfoDto(
    val count: Int,
    val pages: Int,
    val next: String?,
    val prev: String?
)

fun ResultsInfoDto.nextPage(): Int? {
    return next?.split("=")?.last()?.toInt()
}

fun ResultsInfoDto.prevPage(): Int? {
    return prev?.split("=")?.last()?.toInt()
}
