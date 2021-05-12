package com.takehome.sauravrp.di.components

import com.takehome.sauravrp.di.components.modules.NetworkModule
import com.takehome.sauravrp.network.WebServicesAPI
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class])
interface AppComponent {
    fun webservicesAPI(): WebServicesAPI
}