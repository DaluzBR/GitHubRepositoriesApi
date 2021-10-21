package br.com.daluz.android.apps.githubrepositories.data.services

import br.com.daluz.android.apps.githubrepositories.data.model.Repo
import retrofit2.http.GET
import retrofit2.http.Path

interface GitHubServices {

    @GET("users/{user}/repos")
    suspend fun listRepositories(@Path("user") user: String): List<Repo>
}