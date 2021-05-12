package com.takehome.sauravrp.repository

import com.google.common.truth.Truth.assertThat
import com.takehome.sauravrp.TestDataHelper
import com.takehome.sauravrp.network.models.CharactersDto
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class TestNetworkDtoTransformation {

    @Test
    fun testEmployeeDtoTransformation() {
        val employeeDto = TestDataHelper.characterDto()

        val convertedEmployee = employeeDto.toCharacter()

//        assertThat(convertedEmployee.uuid).isEqualTo(employeeDto.id)
        assertThat(convertedEmployee.name).isEqualTo(employeeDto.name)
//        assertThat(convertedEmployee.phone).isEqualTo(employeeDto.species)
//        assertThat(convertedEmployee.email).isEqualTo(employeeDto.emailAddress)
//        assertThat(convertedEmployee.largePhotoUrl).isEqualTo(employeeDto.photoUrlLarge)
//        assertThat(convertedEmployee.smallPhotoUrl).isEqualTo(employeeDto.photoUrlSmall)
//        assertThat(convertedEmployee.team).isEqualTo(employeeDto.team)
//        assertThat(convertedEmployee.biography).isEqualTo(employeeDto.biography)
//        assertThat(convertedEmployee.type.toString()).isEqualTo(employeeDto.employeeType)
    }

    @Test
    fun testEmployeeDtoTransformationWithEmptyValues() {
        val employeesDto = TestDataHelper.characterDtoWithEmptyValues()

        val convertedEmployee = employeesDto.toCharacter()

//        assertThat(convertedEmployee.uuid).matches(employeesDto.id)
        assertThat(convertedEmployee.name).matches(employeesDto.name)
//        assertThat(convertedEmployee.phone).isAnyOf(employeesDto.species, "")
//        assertThat(convertedEmployee.email).matches(employeesDto.emailAddress)
//        assertThat(convertedEmployee.largePhotoUrl).isAnyOf(employeesDto.photoUrlLarge, "")
//        assertThat(convertedEmployee.smallPhotoUrl).isAnyOf(employeesDto.photoUrlSmall, "")
//        assertThat(convertedEmployee.team).isAnyOf(employeesDto.team, "")
//        assertThat(convertedEmployee.biography).isAnyOf(employeesDto.biography, "")
//        assertThat(convertedEmployee.type.toString()).matches(employeesDto.employeeType)
    }

//    @Test
//    fun testEmployeesDtoTransformationWith() {
//        val employeesDto = CharactersDto(emptyList())
//
//        val convertedEmployees = employeesDto.toCharacters()
//
//        assertThat(employeesDto.characters.size).isEqualTo(convertedEmployees.characters.size)
//    }
}