package com.takehome.sauravrp.di.components

import com.takehome.sauravrp.di.components.modules.CharactersViewModelFactoryModule
import com.takehome.sauravrp.di.scopes.DirectoryScope
import com.takehome.sauravrp.viewmodels.CharactersViewModelFactory
import dagger.Component

@DirectoryScope
@Component(
    dependencies = [AppComponent::class],
    modules = [CharactersViewModelFactoryModule::class]
)
interface DirectoryComponent {

    fun viewModelFactory(): CharactersViewModelFactory

    @Component.Factory
    interface Factory {
        fun create(appComponent: AppComponent): DirectoryComponent
    }
}