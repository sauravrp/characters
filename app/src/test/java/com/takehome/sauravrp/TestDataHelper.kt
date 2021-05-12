package com.takehome.sauravrp

import com.takehome.sauravrp.network.models.CharacterDto

object TestDataHelper {
    fun characterDto() = CharacterDto(uuid = "1",
        fullName = "John",
        phoneNumber = "111",
        emailAddress = "email@email.com",
        biography = "android developer",
        photoUrlLarge = "largeUrl",
        photoUrlSmall = "smallUrl",
        team = "Engineering",
        employeeType = "CONTRACTOR")

    fun characterDtoWithEmptyValues()  = CharacterDto(uuid = "1",
        fullName = "John",
        phoneNumber = null,
        emailAddress = "email@email.com",
        biography = null,
        photoUrlLarge = "",
        photoUrlSmall = "",
        team = "Engineering",
        employeeType = "FULL_TIME")
}