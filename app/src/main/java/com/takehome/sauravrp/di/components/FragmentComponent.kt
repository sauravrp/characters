package com.takehome.sauravrp.di.components

import com.takehome.sauravrp.di.scopes.FragmentScope
import com.takehome.sauravrp.views.CharactersDetailsFragment
import com.takehome.sauravrp.views.CharactersFragment
import dagger.Component

@FragmentScope
@Component(dependencies = [DirectoryComponent::class])
interface FragmentComponent {
    fun inject(fragment: CharactersDetailsFragment)
    fun inject(fragment: CharactersFragment)
}