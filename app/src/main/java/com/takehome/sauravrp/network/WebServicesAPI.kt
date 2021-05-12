package com.takehome.sauravrp.network

import com.takehome.sauravrp.network.models.CharactersDto
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET

interface WebServicesAPI {
    @GET("character")
    fun getAllCharacters(): Single<CharactersDto>
}