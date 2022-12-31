package com.example.movieapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.basemodule.basemodule.BaseActivity
import com.example.basemodule.basemodule.BaseViewModel
import com.example.movieapp.movieslist.view.MoviesListFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<BaseViewModel>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setContainerView(R.id.mainLayout)
        addFragment(MoviesListFragment())
    }
}