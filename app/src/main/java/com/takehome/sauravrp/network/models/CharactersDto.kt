package com.takehome.sauravrp.network.models

import com.squareup.moshi.Json

data class CharactersDto(
    @field:Json(name = "employees") val characters: List<CharacterDto>
)