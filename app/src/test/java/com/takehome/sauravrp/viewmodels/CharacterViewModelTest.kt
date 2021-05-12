package com.takehome.sauravrp.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.google.common.truth.Truth.assertThat
import com.takehome.sauravrp.repository.DirectoryRepository
import io.mockk.*
import io.mockk.impl.annotations.MockK
import io.reactivex.rxjava3.android.plugins.RxAndroidPlugins.reset
import io.reactivex.rxjava3.android.plugins.RxAndroidPlugins.setInitMainThreadSchedulerHandler
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.plugins.RxJavaPlugins
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.PublishSubject
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class CharacterViewModelTest {

    private lateinit var viewModel: CharacterViewModel

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @MockK
    lateinit var directoryRepository: DirectoryRepository

    @Before
    fun setup() {
        /**
         * https://stackoverflow.com/questions/46549405/testing-asynchronous-rxjava-code-android
         */
        reset()
        RxJavaPlugins.reset()
        RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
        setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
        MockKAnnotations.init(this)
        viewModel = CharacterViewModel(directoryRepository)
    }

    @After
    fun tearDown() {
        reset()
        RxJavaPlugins.reset()
    }

    @Test
    fun `when fetch characters called, then loading return true`() {
        val mockedObserver = createCharactersObserver()

        // https://stackoverflow.com/questions/48980897/unit-testing-rxjava-doonsubscribe-and-dofinally
        val data = Characters(emptyList())
        val delayer = PublishSubject.create<Boolean>()
        every { (directoryRepository.getCharacterDirectory()) } returns
                Single.just(data).delaySubscription(delayer)

        // when
        viewModel.viewState.observeForever(mockedObserver)

        // then
        val slot = slot<CharacterViewModel.ViewState>()
        verify { mockedObserver.onChanged(capture(slot)) }

        assertThat(slot.captured).isEqualTo(CharacterViewModel.ViewState.Loading)
    }


    @Test
    fun `when fetch characters called is successful, then view statue return success state`() {
        val mockedObserver = createCharactersObserver()

        val data = Characters(emptyList())
        every { (directoryRepository.getCharacterDirectory()) } returns
                Single.just(data)

        // when
        viewModel.viewState.observeForever(mockedObserver)

        // then
        val slot = slot<CharacterViewModel.ViewState>()
        verify { mockedObserver.onChanged(capture(slot)) }

        assertThat(slot.captured).isInstanceOf(CharacterViewModel.ViewState.Success::class.java)
        assertThat((slot.captured as CharacterViewModel.ViewState.Success).data)
            .isEqualTo(data)
    }

    @Test
    fun `when fetch characters called failed, then view statue return error state`() {
        val mockedObserver = createCharactersObserver()

        val error = Throwable("Some Error")
        every { (directoryRepository.getCharacterDirectory()) } returns
                Single.error(error)

        // when
        viewModel.viewState.observeForever(mockedObserver)

        // then
        val slot = slot<CharacterViewModel.ViewState>()
        verify { mockedObserver.onChanged(capture(slot)) }

        assertThat(slot.captured).isInstanceOf(CharacterViewModel.ViewState.Error::class.java)
        assertThat((slot.captured as CharacterViewModel.ViewState.Error).error)
            .isEqualTo(error)
    }

    private fun createCharactersObserver(): Observer<CharacterViewModel.ViewState> =
        spyk(Observer { })
}