package com.takehome.sauravrp.repository

import com.google.common.truth.Truth.assertThat
import com.takehome.sauravrp.TestDataHelper
import com.takehome.sauravrp.network.WebServicesAPI
import com.takehome.sauravrp.network.models.CharactersDto
import com.takehome.sauravrp.viewmodels.Characters
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.observers.TestObserver
import org.junit.Before
import org.junit.Test

class DirectoryRepositoryTest {

    private lateinit var directoryRepository: DirectoryRepository

    @MockK
    lateinit var webServicesAPI: WebServicesAPI

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        directoryRepository = DirectoryRepository(webServicesAPI)
    }

    @Test
    fun `when services returns data, correct type is returned`() {
        val mockedObserver = TestObserver<Characters>()

        val characterDto = TestDataHelper.characterDto()
        val charactersDto = CharactersDto(listOf(characterDto))

        every { webServicesAPI.getEmployeeDirectory() } returns
                Single.just(charactersDto)
        // when
        directoryRepository.getCharacterDirectory().subscribe(mockedObserver)

       // then
        assertThat(mockedObserver.values().first()).isInstanceOf(Characters::class.java)
    }

    @Test
    fun `when services returns error, error is returned`() {
        val mockedObserver = TestObserver<Characters>()

        val throwable = Throwable("Some Error")

        every { webServicesAPI.getEmployeeDirectory() } returns
                Single.error(throwable)
        // when
        directoryRepository.getCharacterDirectory().subscribe(mockedObserver)

        // then
        mockedObserver.assertError(throwable)
    }
}