package br.com.daluz.android.apps.githubrepositories.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import br.com.daluz.android.apps.githubrepositories.data.model.Repo
import br.com.daluz.android.apps.githubrepositories.databinding.FragmentRepoBinding
import com.bumptech.glide.Glide


class RepoFragment : Fragment() {

    private val binding by lazy { FragmentRepoBinding.inflate(layoutInflater) }
    private val navigationArgs: RepoFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val repo: Repo = navigationArgs.repo

        bind(repo)
    }

    private fun bind(repo: Repo) {
        Glide.with(binding.root.context).load(repo.owner.avatarURL).into(binding.imvRepoOwner)
        binding.txvRepoLogin.text = repo.owner.login
        binding.txvRepoNameInfo.text = repo.name
        binding.txvRepoDescriptionInfo.text = repo.description
        binding.txvRepoLanguageInfo.text = repo.language
        binding.chpRepoStargazersCount.text = repo.stargazersCount.toString()
        binding.chpRepoWatchersCount.text = repo.watchersCount.toString()
        binding.btnRepoUrl.setOnClickListener {
            goToGitHubRepository(repo.htmlURL)
        }
    }

    private fun goToGitHubRepository(httpUrl: String) {
        val queryUrl: Uri = Uri.parse(httpUrl)
        val intent = Intent(Intent.ACTION_VIEW, queryUrl)
        requireContext().startActivity(intent)
    }
}