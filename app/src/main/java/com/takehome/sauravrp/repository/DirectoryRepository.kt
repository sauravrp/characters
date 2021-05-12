package com.takehome.sauravrp.repository

import com.takehome.sauravrp.BuildConfig
import com.takehome.sauravrp.di.scopes.DirectoryScope
import com.takehome.sauravrp.network.WebServicesAPI
import com.takehome.sauravrp.network.models.CharacterDto
import com.takehome.sauravrp.network.models.CharactersDto
import com.takehome.sauravrp.network.models.ResultsInfoDto
import com.takehome.sauravrp.viewmodels.Character
import com.takehome.sauravrp.viewmodels.Characters
import com.takehome.sauravrp.viewmodels.Info
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

@DirectoryScope
class DirectoryRepository @Inject constructor(private val webServicesAPI: WebServicesAPI) {
    fun getCharacterDirectory(): Single<Characters> {
        return webServicesAPI.getAllCharacters().map { it.toCharacters() }
    }
}

// Network dto converted to models consumed by views
fun CharacterDto.toCharacter(): Character {
    return Character(
        id = id,
        name = name,
        species = species,
        type = type,
        gender = gender,
        image = image
    )
}

fun ResultsInfoDto.toInfo(): Info {
    return Info(count = count, pages = pages, next = next, prev = prev)
}

fun CharactersDto.toCharacters(): Characters {
    return Characters(this.info.toInfo(),
        this.characters.map { it.toCharacter() })
}

