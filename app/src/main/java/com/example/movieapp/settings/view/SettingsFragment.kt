package com.example.movieapp.settings.view

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import com.example.basemodule.basemodule.BaseFragment
import com.example.movieapp.R
import com.example.movieapp.shared.model.MoviesListType
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.back_button_top_bar.*
import kotlinx.android.synthetic.main.fragment_settings.*

@AndroidEntryPoint
class SettingsFragment : BaseFragment<SettingsViewModel>() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        header_text.text = getString(R.string.settings)
        viewModel.getSortType(requireContext())
    }

    override fun initObservers() {
        super.initObservers()
        viewModel.sortTypeLiveData.observe(this){
            if (it == MoviesListType.MOST_POPULAR){
                updateSortTypeView(false)
            }
            else {
                updateSortTypeView(true)
            }
        }
    }

    override fun initListeners() {
        super.initListeners()
        back_button.setOnClickListener {
            onBackButtonClicked()
        }
    }

    private fun onBackButtonClicked() {
        val type = if (topRated.isChecked) MoviesListType.TOP_RATED else MoviesListType.MOST_POPULAR
        viewModel.updateSortType(requireContext(),type)
        setFragmentResult(SETTINGS_RESULT,bundleOf(SELECTED_TYPE to type))
        finishFragment()
    }

    private fun updateSortTypeView(topRatedSelected: Boolean){
        topRated.isChecked = topRatedSelected
        mostPopular.isChecked = !topRatedSelected
    }

    companion object{
        const val SETTINGS_RESULT = "SETTINGS_RESULT"
        const val SELECTED_TYPE = "SELECTED_TYPE"
    }

}