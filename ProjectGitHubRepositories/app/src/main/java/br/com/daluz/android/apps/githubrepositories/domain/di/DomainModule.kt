package br.com.daluz.android.apps.githubrepositories.domain.di

import br.com.daluz.android.apps.githubrepositories.domain.RepoListUseCases
import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module
import org.koin.dsl.module

object DomainModule {

    fun load() {
        loadKoinModules(useCaseModule())
    }

    private fun useCaseModule(): Module {
        return module {
            factory {
                RepoListUseCases(repository = get())
            }
        }
    }
}