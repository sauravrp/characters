package com.takehome.sauravrp.di.components

import com.takehome.sauravrp.di.components.modules.ViewModelFactoryModule
import com.takehome.sauravrp.di.scopes.DirectoryScope
import com.takehome.sauravrp.viewmodels.CharacterDetailViewModelFactory
import com.takehome.sauravrp.viewmodels.CharactersViewModelFactory
import dagger.Component

@DirectoryScope
@Component(
    dependencies = [AppComponent::class],
    modules = [ViewModelFactoryModule::class]
)
interface DirectoryComponent {
    fun charactersViewModelFactory(): CharactersViewModelFactory
    fun charactersDetailViewModelFactory(): CharacterDetailViewModelFactory
}