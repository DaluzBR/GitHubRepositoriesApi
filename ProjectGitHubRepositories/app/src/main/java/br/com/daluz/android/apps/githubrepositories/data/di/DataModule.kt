package br.com.daluz.android.apps.githubrepositories.data.di

import android.util.Log
import br.com.daluz.android.apps.githubrepositories.data.repositories.GitHubRepository
import br.com.daluz.android.apps.githubrepositories.data.repositories.GitHubRepositoryImpl
import br.com.daluz.android.apps.githubrepositories.data.services.GitHubServices
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object DataModule {

    private const val OK_HTTP = "OkHttp"
    private const val BASE_URL = "https://api.github.com/"

    fun load() {
        loadKoinModules(
            networkModules() +
                    repositoriesModule()
        )
    }

    private inline fun <reified T> createService(
        client: OkHttpClient,
        factory: GsonConverterFactory
    ): T {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(factory)
            .build().create(T::class.java)
    }

    private fun networkModules(): Module {
        return module {
            single {
                val interceptor = HttpLoggingInterceptor {
                    Log.d(OK_HTTP, it)
                }
                interceptor.level = HttpLoggingInterceptor.Level.BODY

                OkHttpClient.Builder()
                    .addInterceptor(interceptor)
                    .build()
            }

            single {
                GsonConverterFactory.create(GsonBuilder().create())
            }

            single {
                createService<GitHubServices>(client = get(), factory = get())
            }
        }
    }

    private fun repositoriesModule(): Module {
        return module {
            single<GitHubRepository> {
                GitHubRepositoryImpl(service = get())
            }
        }
    }

}