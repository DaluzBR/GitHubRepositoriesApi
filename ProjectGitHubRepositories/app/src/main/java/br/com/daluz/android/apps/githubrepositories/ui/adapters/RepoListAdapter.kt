package br.com.daluz.android.apps.githubrepositories.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import br.com.daluz.android.apps.githubrepositories.data.model.Repo
import br.com.daluz.android.apps.githubrepositories.databinding.AdapterItemRepositoryBinding
import com.bumptech.glide.Glide

class RepoListAdapter(
    private val onRepoItemClicked: (Repo) -> Unit
) : ListAdapter<Repo, RepoListAdapter.ViewHolder>(RepoListDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = AdapterItemRepositoryBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.itemView.setOnClickListener {
            onRepoItemClicked(item)
        }
        holder.bind(item)
    }

    inner class ViewHolder(
        private val binding: AdapterItemRepositoryBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Repo) {
            binding.txvRepoName.text = item.name
            binding.txvRepoDescription.text = item.description
            binding.txvRepoLanguage.text = item.language
            binding.chpRepoStargazersCount.text = item.stargazersCount.toString()

            Glide.with(binding.root.context)
                .load(item.owner.avatarURL).into(binding.imvAvatar)
        }
    }
}

class RepoListDiffCallback : DiffUtil.ItemCallback<Repo>() {
    override fun areItemsTheSame(oldItem: Repo, newItem: Repo) = oldItem == newItem
    override fun areContentsTheSame(oldItem: Repo, newItem: Repo) = oldItem.id == newItem.id
}