package br.com.daluz.android.apps.githubrepositories.domain

import br.com.daluz.android.apps.githubrepositories.core.UseCase
import br.com.daluz.android.apps.githubrepositories.data.model.Repo
import br.com.daluz.android.apps.githubrepositories.data.repositories.GitHubRepository
import kotlinx.coroutines.flow.Flow

class RepoListUseCases(
    private val repository: GitHubRepository
) : UseCase<String, List<Repo>>() {

    override suspend fun execute(param: String): Flow<List<Repo>> {
        return repository.listRepositories(param)
    }
}