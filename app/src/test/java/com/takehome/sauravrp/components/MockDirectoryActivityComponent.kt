package com.takehome.sauravrp.components

import com.takehome.sauravrp.components.modules.MockCharacterViewModelFactoryModule
import com.takehome.sauravrp.components.modules.MockNetworkModule
import com.takehome.sauravrp.components.scopes.TestScope
import com.takehome.sauravrp.di.components.AppComponent
import com.takehome.sauravrp.di.components.DirectoryComponent
import dagger.Component

@TestScope
@Component(
    modules = [MockCharacterViewModelFactoryModule::class,
        MockNetworkModule::class]
)
interface MockDirectoryActivityComponent : DirectoryComponent, AppComponent