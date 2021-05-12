package com.takehome.sauravrp.di.components.modules

import com.takehome.sauravrp.di.scopes.DirectoryScope
import com.takehome.sauravrp.repository.DirectoryRepository
import com.takehome.sauravrp.viewmodels.CharactersViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class CharactersViewModelFactoryModule  {

    @Provides
    @DirectoryScope
    fun providesViewModelFactory(directoryRepository: DirectoryRepository) : CharactersViewModelFactory {
        return CharactersViewModelFactory(directoryRepository)
    }
}