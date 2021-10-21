package br.com.daluz.android.apps.githubrepositories.data.repositories

import br.com.daluz.android.apps.githubrepositories.data.model.Repo
import kotlinx.coroutines.flow.Flow

interface GitHubRepository {

    suspend fun listRepositories(user: String): Flow<List<Repo>>

}