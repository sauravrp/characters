package com.takehome.sauravrp.repository

import com.takehome.sauravrp.BuildConfig
import com.takehome.sauravrp.di.scopes.DirectoryScope
import com.takehome.sauravrp.network.WebServicesAPI
import com.takehome.sauravrp.network.models.CharacterDto
import com.takehome.sauravrp.network.models.CharactersDto
import com.takehome.sauravrp.viewmodels.Character
import com.takehome.sauravrp.viewmodels.EmployeeType
import com.takehome.sauravrp.viewmodels.Characters
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

@DirectoryScope
class DirectoryRepository @Inject constructor(private val webServicesAPI: WebServicesAPI) {
    fun getCharacterDirectory(): Single<Characters> {
        return webServicesAPI.getEmployeeDirectory().map { it.toCharacters() }
    }

    // for testing only
    fun getEmployeeDirectoryMalformed(): Single<Characters> {
        return if(BuildConfig.DEBUG) {
            webServicesAPI.getMalformedEmployeeDirectory().map { it.toCharacters() }
        } else {
            throw Exception("using debug only malformed url")
        }
    }

    // for testing only, if prod,
    fun getEmptyEmployeeDirectory(): Single<Characters> {
        return if(BuildConfig.DEBUG) {
            webServicesAPI.getEmptyEmployeeDirectory().map { it.toCharacters() }
        } else {
            throw Exception("using debug only empty url")
        }
    }
}

// Network dto converted to models consumed by views
fun CharacterDto.toCharacter(): Character {
    return Character(
        uuid = uuid,
        name = fullName,
        phone = phoneNumber.orEmpty(),
        email = emailAddress,
        biography = biography.orEmpty(),
        smallPhotoUrl = photoUrlSmall.orEmpty(),
        largePhotoUrl = photoUrlLarge.orEmpty(),
        team = team,
        type = EmployeeType.valueOf(employeeType)
    )
}

fun CharactersDto.toCharacters(): Characters {
    return Characters(this.characters.map { it.toCharacter() })
}