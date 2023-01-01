package com.example.movieapp.moviedetails

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.basemodule.basemodule.BaseFragment
import com.example.movieapp.R
import com.example.movieapp.shared.model.Movie
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.back_button_top_bar.*
import kotlinx.android.synthetic.main.fragment_movie_details.*
import kotlinx.android.synthetic.main.movie_list_item.view.*

@AndroidEntryPoint
class MovieDetailsFragment : BaseFragment<MovieDetailsViewModel>() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movie_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.extractArguments(arguments)
        header_text.text = getString(R.string.movie_details)
    }

    override fun initObservers() {
        super.initObservers()
        viewModel.getMovieLiveData().observe(this){
            updateView(it)
        }
    }

    override fun initListeners() {
        super.initListeners()
        back_button.setOnClickListener {
            finishFragment()
        }
    }

    private fun updateView(movie: Movie) {
        if (movie.imagePath != null) {
            Glide
                .with(requireContext())
                .load("https://image.tmdb.org/t/p/w185/" + movie.imagePath)
                .into(movie_image_view)

            Glide
                .with(requireContext())
                .load("https://image.tmdb.org/t/p/w500/" + movie.imagePath)
                .into(movie_background)
        }
        movie_title.text = movie.title
        movie_release_date.text = "Released in "+movie.releaseDate
        rating_bar.rating = movie.userRating.toFloat()
        overview_text.text = movie.overview
        movie_rating.text = movie.userRating.toString()
    }

    companion object{
         const val MOVIE_ITEM = "MOVIE_ITEM"
        fun createInstance(movie: Movie): MovieDetailsFragment{
            val fragment = MovieDetailsFragment()
            val bundle = Bundle()
            bundle.putParcelable(MOVIE_ITEM,movie)
            fragment.arguments = bundle
            return fragment
        }
    }

}