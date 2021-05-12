package com.takehome.sauravrp.repository

import com.takehome.sauravrp.db.AppDatabase
import com.takehome.sauravrp.di.scopes.DirectoryScope
import com.takehome.sauravrp.network.WebServicesAPI
import com.takehome.sauravrp.network.models.CharacterDto
import com.takehome.sauravrp.network.models.CharactersDto
import com.takehome.sauravrp.network.models.ResultsInfoDto
import com.takehome.sauravrp.viewmodels.ShowCharacter
import com.takehome.sauravrp.viewmodels.Characters
import com.takehome.sauravrp.viewmodels.Info
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

@DirectoryScope
class DirectoryRepository @Inject constructor(private val webServicesAPI: WebServicesAPI,
                                              private val appDatabase: AppDatabase) {
    fun getCharacterDirectory(): Single<Characters> {
        return appDatabase.characterDao().getAllCharacters().flatMap { cachedData ->
            if (cachedData.isEmpty()) {
                webServicesAPI.getAllCharacters().map { it.toCharacters() }.flatMap {
                    appDatabase.characterDao().insertCharacters(*it.characters.toTypedArray()).andThen(Single.just(it))
                }
            } else {
                Single.just(Characters(null, cachedData))
            }
        }

    }

    fun getCharacterDetail(id: String) : Single<ShowCharacter> {
        return appDatabase.characterDao().getCharacter(id)
    }
}

// Network dto converted to models consumed by views
fun CharacterDto.toCharacter(): ShowCharacter {
    return ShowCharacter(
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

