package com.takehome.sauravrp.repository

import androidx.paging.PagingState
import androidx.paging.rxjava3.RxPagingSource
import com.takehome.sauravrp.BuildConfig
import com.takehome.sauravrp.di.scopes.DirectoryScope
import com.takehome.sauravrp.network.WebServicesAPI
import com.takehome.sauravrp.network.models.*
import com.takehome.sauravrp.viewmodels.Character
import com.takehome.sauravrp.viewmodels.Characters
import com.takehome.sauravrp.viewmodels.Info
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@DirectoryScope
class DirectoryRepository @Inject constructor(private val webServicesAPI: WebServicesAPI) : RxPagingSource<Int, Character>() {
    fun getCharacterDirectory(): Single<Characters> {
        return webServicesAPI.getAllCharacters(1).map { it.toCharacters() }
    }

    override fun getRefreshKey(state: PagingState<Int, Character>): Int? {
        return state.anchorPosition
    }

    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, Character>> {
        val nextPage = params.key ?: 1

        return webServicesAPI
            .getAllCharacters(nextPage)
            .subscribeOn(Schedulers.io())
            .map (this::toLoadResult)
            .onErrorReturn { e ->
                LoadResult.Error(e)
            }
    }

    private fun toLoadResult(charactersDto: CharactersDto) : LoadResult<Int, Character> {
        val convertedData = charactersDto.characters.map { it.toCharacter() }.toList()
        return LoadResult.Page<Int, Character>(
            data = convertedData,
            nextKey = charactersDto.info.nextPage(),
            prevKey = charactersDto.info.prevPage()
        ).also {
            println("saurav = ${it.nextKey} ${it.prevKey}")
        }
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

