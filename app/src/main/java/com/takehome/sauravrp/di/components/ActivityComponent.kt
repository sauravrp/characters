package com.takehome.sauravrp.di.components

import com.takehome.sauravrp.di.scopes.ActivityScope
import com.takehome.sauravrp.views.CharacterDetailActivity
import com.takehome.sauravrp.views.CharactersActivity
import dagger.Component

@ActivityScope
@Component(dependencies = [DirectoryComponent::class])
interface ActivityComponent {

    fun inject(activity: CharactersActivity)
    fun inject(activity: CharacterDetailActivity)
}