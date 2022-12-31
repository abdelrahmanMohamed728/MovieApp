package com.example.movieapp.movieslist.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.basemodule.basemodule.BaseFragment
import com.example.movieapp.R
import com.example.movieapp.movieslist.view.adapter.MoviesListAdapter
import com.example.movieapp.movieslist.viewmodel.MoviesListViewModel
import dagger.hilt.android.AndroidEntryPoint
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
        initMoviesRecycler()
        viewModel.getMovies()
    }

    private fun initMoviesRecycler(){
        val layoutManager = GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)
        movies_recycler.layoutManager = layoutManager
        moviesListAdapter = MoviesListAdapter()
        movies_recycler.adapter = moviesListAdapter
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