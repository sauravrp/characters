package com.takehome.sauravrp.network.models

import com.squareup.moshi.Json

data class CharactersDto(
    @field:Json(name = "info") val info: ResultsInfoDto,
    @field:Json(name = "results") val characters: List<CharacterDto>
)