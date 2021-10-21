package br.com.daluz.android.apps.githubrepositories.ui

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import br.com.daluz.android.apps.githubrepositories.R
import br.com.daluz.android.apps.githubrepositories.core.createDialog
import br.com.daluz.android.apps.githubrepositories.core.createProgressDialog
import br.com.daluz.android.apps.githubrepositories.core.hideSoftKeyboard
import br.com.daluz.android.apps.githubrepositories.databinding.FragmentMainListBinding
import br.com.daluz.android.apps.githubrepositories.presentation.MainViewModel
import br.com.daluz.android.apps.githubrepositories.ui.adapters.RepoListAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainListFragment : Fragment(),
    SearchView.OnQueryTextListener {

    private val mainViewModel by viewModel<MainViewModel>()
    private val progressDialog by lazy { requireContext().createProgressDialog() }
    private val binding by lazy { FragmentMainListBinding.inflate(layoutInflater) }
    private lateinit var adapter: RepoListAdapter
    private var searchQuery: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = RepoListAdapter { repo ->
            val action = MainListFragmentDirections
                .actionMainListFragmentToRepoFragment(repo)
            findNavController().navigate(action)
        }

        binding.rcvRepoList.adapter = adapter

        checkEmptyList()

        mainViewModel.repos.observe(viewLifecycleOwner) {
            when (it) {
                MainViewModel.RepoState.Loading -> {
                    progressDialog.show()
                }
                is MainViewModel.RepoState.Error -> {
                    showDialog(
                        getString(
                            R.string.message_search_error_user_not_found,
                            searchQuery
                        )
                    )
                    progressDialog.dismiss()
                }
                is MainViewModel.RepoState.Success -> {
                    if (it.list.isEmpty()) {
                        showDialog(getString(R.string.message_search_item_not_found))
                        adapter.submitList(null)
                    } else {
                        adapter.submitList(it.list)
//                        val listSize = adapter.currentList.size
//                        requireContext().toast(
//                            resources.getQuantityString(
//                                R.plurals.message_repo_list_size,
//                                listSize,
//                                listSize
//                            )
//                        )
                    }
                    progressDialog.dismiss()
                }
            }
            checkEmptyList()
        }
    }

    private fun showDialog(message: String) {
        requireContext().createDialog {
            setMessage(message)
        }.show()
    }

    private fun checkEmptyList() {
        if (adapter.currentList.isEmpty()) {
            binding.rcvRepoList.visibility = View.GONE
            binding.imvRepoEmptyList.visibility = View.VISIBLE
        } else {
            binding.rcvRepoList.visibility = View.VISIBLE
            binding.imvRepoEmptyList.visibility = View.GONE
        }
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        inflater.inflate(R.menu.menu_search, menu)
        val searchView = menu.findItem(R.id.action_search).actionView as SearchView
        searchView.queryHint = getString(R.string.label_search_hint)
        searchView.setOnQueryTextListener(this)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        searchQuery = query ?: ""
        query?.let { word ->
            mainViewModel.getRepoList(word)
        }
        binding.root.hideSoftKeyboard()
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return false
    }

}