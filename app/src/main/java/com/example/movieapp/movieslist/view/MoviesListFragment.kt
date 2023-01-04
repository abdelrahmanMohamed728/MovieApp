package com.example.movieapp.movieslist.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.example.basemodule.basemodule.BaseFragment
import com.example.movieapp.R
import com.example.movieapp.moviedetails.MovieDetailsFragment
import com.example.movieapp.movieslist.view.adapter.MoviesListAdapter
import com.example.movieapp.movieslist.view.adapter.OnMovieClicked
import com.example.movieapp.movieslist.viewmodel.MoviesListViewModel
import com.example.movieapp.settings.view.SettingsFragment
import com.example.movieapp.shared.model.Movie
import com.example.movieapp.shared.model.MoviesListType
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.back_button_top_bar.*
import kotlinx.android.synthetic.main.fragment_movies_list.*
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MoviesListFragment : BaseFragment<MoviesListViewModel>() {

    private lateinit var moviesListAdapter: MoviesListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movies_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.updateSortType(requireContext())
        initMoviesRecycler()
        initTopBar()
        viewModel.getMovies()
    }

    private fun initTopBar() {
        header_text.text = getString(R.string.home)
        back_button.setImageResource(R.drawable.ic_setting)
        back_button.setOnClickListener {
            setFragmentResultListener(SettingsFragment.SETTINGS_RESULT){
                requestKey, bundle ->
                if (bundle.containsKey(SettingsFragment.SELECTED_TYPE)){
                    viewModel.sortType =
                        bundle.getParcelable(SettingsFragment.SELECTED_TYPE)
                            ?: MoviesListType.MOST_POPULAR
                    viewModel.getMovies()
                }
            }
            addFragment(SettingsFragment())
        }
    }

    private fun initMoviesRecycler() {
        val layoutManager =
            GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)
        movies_recycler.layoutManager = layoutManager
        moviesListAdapter = MoviesListAdapter(object : OnMovieClicked {
            override fun onMovieClicked(movie: Movie) {
                addFragment(MovieDetailsFragment.createInstance(movie))
            }
        })
        movies_recycler.adapter = moviesListAdapter
        initMoviesLoading()
    }

    private fun initMoviesLoading() {
        moviesListAdapter.addLoadStateListener {
            if (it.refresh is LoadState.Loading){
                progress_bar.visibility = View.VISIBLE
            }else{
                progress_bar.visibility = View.GONE
            }
        }

    }

    override fun initObservers() {
        super.initObservers()
        viewModel.getMoviesLiveData().observe(this) {
            lifecycleScope.launch {
                moviesListAdapter.submitData(it)
            }
        }
    }
}