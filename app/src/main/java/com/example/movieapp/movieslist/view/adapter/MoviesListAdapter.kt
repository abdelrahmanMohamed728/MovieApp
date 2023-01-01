package com.example.movieapp.movieslist.view.adapter

import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.basemodule.basemodule.pagination.BasePagingDataAdapter
import com.example.movieapp.R
import com.example.movieapp.shared.model.Movie
import kotlinx.android.synthetic.main.movie_list_item.view.*

class MoviesListAdapter(val onMovieClicked: OnMovieClicked) :
    BasePagingDataAdapter<Movie, MoviesListAdapter.ViewHolder>(Comp) {

    override fun getItemLayout(viewType: Int): Int {
        return R.layout.movie_list_item
    }

    override fun getViewHolderConstructor(
        view: View,
        viewType: Int
    ): MoviesListAdapter.ViewHolder {
        return MoviesListAdapter.ViewHolder(view,onMovieClicked)
    }

    class ViewHolder(view: View,val onMovieClicked: OnMovieClicked) :
        BaseViewHolder<Movie>(view) {
        override fun bindView(item: Movie){
            if (item.imagePath != null) {
                Glide
                    .with(itemView.context)
                    .load("https://image.tmdb.org/t/p/w185/" + item.imagePath)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(itemView.list_image_view)
            }
            itemView.list_image_view.setOnClickListener {
                onMovieClicked.onMovieClicked(item)
            }
        }
    }

    object Comp : BasePagingDataAdapter.BaseComparator<Movie>() {
        override fun compareItems(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.title == newItem.title
        }

        override fun compareContentsItems(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }

    }
}