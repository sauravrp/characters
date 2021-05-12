package com.takehome.sauravrp.network

import com.takehome.sauravrp.network.models.CharactersDto
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET

interface WebServicesAPI {
    @GET("sq-mobile-interview/employees.json")
    fun getEmployeeDirectory(): Single<CharactersDto>

    @GET("sq-mobile-interview/employees_malformed.json")
    fun getMalformedEmployeeDirectory(): Single<CharactersDto>

    @GET("sq-mobile-interview/employees_empty.json")
    fun getEmptyEmployeeDirectory(): Single<CharactersDto>
}