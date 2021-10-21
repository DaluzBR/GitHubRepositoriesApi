package br.com.daluz.android.apps.githubrepositories.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.daluz.android.apps.githubrepositories.data.model.Repo
import br.com.daluz.android.apps.githubrepositories.domain.RepoListUseCases
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class MainViewModel(
    private val repoUseCases: RepoListUseCases
) : ViewModel() {

    private val _repos = MutableLiveData<RepoState>()
    val repos: LiveData<RepoState> = _repos

    fun getRepoList(user: String) {
        viewModelScope.launch {
            repoUseCases(user)
                .onStart {
                    // Iniciando.
                    _repos.postValue(RepoState.Loading)
                }
                .catch {
                    // Exceção (erro).
                    _repos.postValue(RepoState.Error(it))
                }
                .collect {
                    // Finalizado.
                    _repos.postValue(RepoState.Success(it))
                }
        }
    }

    sealed class RepoState {
        object Loading : RepoState()
        data class Success(val list: List<Repo>) : RepoState()
        data class Error(val error: Throwable) : RepoState()
    }
}