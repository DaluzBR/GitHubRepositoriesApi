package br.com.daluz.android.apps.githubrepositories.data.repositories

import br.com.daluz.android.apps.githubrepositories.core.RemoteException
import br.com.daluz.android.apps.githubrepositories.data.services.GitHubServices
import kotlinx.coroutines.flow.flow

class GitHubRepositoryImpl(
    private val service: GitHubServices
) : GitHubRepository {

    override suspend fun listRepositories(user: String) = flow {
        try {
            val repoList = service.listRepositories(user)
            emit(repoList)
        } catch (e: Exception) {
            throw RemoteException(e.message ?: "Não foi possivel fazer a busca no momento!")
        }
    }
}