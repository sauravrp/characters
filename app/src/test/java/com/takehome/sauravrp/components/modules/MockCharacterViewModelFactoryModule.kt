package com.takehome.sauravrp.components.modules

import androidx.lifecycle.ViewModel
import com.takehome.sauravrp.components.scopes.TestScope
import com.takehome.sauravrp.repository.DirectoryRepository
import com.takehome.sauravrp.viewmodels.CharacterViewModel
import com.takehome.sauravrp.viewmodels.CharactersViewModelFactory
import dagger.Module
import dagger.Provides
import io.mockk.mockk

@Module
class MockCharacterViewModelFactoryModule(private val characterViewModel: CharacterViewModel) {

    @Provides
    @TestScope
    fun directoryRepository(): DirectoryRepository = mockk()

    @Provides
    @TestScope
    fun providesViewModelFactory(directoryRepository: DirectoryRepository): CharactersViewModelFactory {
        return object : CharactersViewModelFactory(directoryRepository) {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return characterViewModel as T
            }
        }
    }
}