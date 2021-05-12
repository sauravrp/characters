package com.takehome.sauravrp.di.components.modules

import com.takehome.sauravrp.di.scopes.DirectoryScope
import com.takehome.sauravrp.repository.DirectoryRepository
import com.takehome.sauravrp.viewmodels.CharacterDetailViewModelFactory
import com.takehome.sauravrp.viewmodels.CharactersViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class ViewModelFactoryModule  {

    @Provides
    @DirectoryScope
    fun providesViewModelFactory(directoryRepository: DirectoryRepository) : CharactersViewModelFactory {
        return CharactersViewModelFactory(directoryRepository)
    }

    @Provides
    @DirectoryScope
    fun providesDetailViewModelFactory(directoryRepository: DirectoryRepository) : CharacterDetailViewModelFactory {
        return CharacterDetailViewModelFactory(directoryRepository)
    }
}