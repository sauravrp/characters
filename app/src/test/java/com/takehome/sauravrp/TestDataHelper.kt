package com.takehome.sauravrp

import com.takehome.sauravrp.network.models.CharacterDto

object TestDataHelper {
    fun characterDto() = CharacterDto(
        id = "1",
        name = "John",
        species = "111",
        type = "some",
        gender = "MALE",
        image = "something"
    )

    fun characterDtoWithEmptyValues() = CharacterDto(
        id = "1",
        name = "John",
        species = "111",
        type = "some",
        gender = "MALE",
        image = "something"
    )
}