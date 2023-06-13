package com.example.data.di

import com.example.domain.di.RepoProvider

import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class, RepositoryModule::class])
interface DataComponent : RepoProvider {
    companion object {
        fun init(): RepoProvider {
            return DaggerDataComponent.create()
        }
    }
}
