package com.example.drdbasemodule

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.drdbasemodule.loading.LoadingUtil
import com.example.drdbasemodule.model.Action
import com.google.android.material.snackbar.Snackbar
import java.lang.reflect.ParameterizedType

open class BaseFragment<T : BaseViewModel> : Fragment(), InitFragment {

    lateinit var viewModel: T
    private lateinit var dialog : Dialog

    @Suppress("UNCHECKED_CAST")
    private fun getViewModelClassType(): Class<T> {
        return (javaClass
            .genericSuperclass as ParameterizedType).actualTypeArguments[0] as Class<T>
    }

    private fun getViewModelForThisClass(): T {
        return ViewModelProvider(this).get(getViewModelClassType())
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = getViewModelForThisClass()
        initRecycler()
        initListeners()
        initObservers()
        swipeToRefreshListener()
    }

    fun popAllBackStack(){
        val manger = activity?.supportFragmentManager
        val backStackEntryCount = manger?.backStackEntryCount ?: 0
        for (i in 0..backStackEntryCount){
            manger?.popBackStack()
        }
    }

    fun hideKeyboardFrom(context: Context, view: View) {
        val imm: InputMethodManager =
            context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    fun removeFragmentFromBackStack(){
        activity?.supportFragmentManager?.popBackStack()
    }

    fun showDialog(fragment: DialogFragment) {
        activity?.supportFragmentManager?.let {
            fragment.show(it, null)
        }
    }

    fun showSnackBar(view: View, message: String) {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG).show()
    }

    fun toast(message: String){
        Toast.makeText(activity,message, Toast.LENGTH_LONG).show()
    }

    fun showInfoPopup(message: String, title:String? = null, onDismiss: () -> Unit = {}){
        val alertDialogBuilder = AlertDialog.Builder(requireContext()).create()
        with(alertDialogBuilder)
        {

            title?.let {
                setTitle(it)
            }
            setMessage(message)

            setButton(AlertDialog.BUTTON_POSITIVE,"ok") { dialogInterface: DialogInterface, i: Int ->
                dialogInterface.dismiss()
                onDismiss()
            }

            show()
        }
    }

    fun finishFragment() {
        if (parentFragment != null)
            requireParentFragment().childFragmentManager.popBackStack()
        else
            requireActivity().supportFragmentManager.popBackStack()
    }

     fun getContainer(): Int {
        var activity = getBaseActivity()
        return activity.container
    }

     fun getBaseActivity(): BaseActivity<BaseViewModel> {
        return activity as BaseActivity<BaseViewModel>
    }

    override fun initObservers() {
        viewModel.errorLiveData.observe(viewLifecycleOwner) {
            doOnError(it)
        }

        viewModel.actionLiveData.observe(viewLifecycleOwner) {
            doAction(it)
        }

        viewModel.addShimmerLiveData.observe(viewLifecycleOwner){
            addShimmerEffect(it)
        }

        viewModel.hideShimmerLiveData.observe(viewLifecycleOwner){
            hideShimmerEffect(it)
        }
    }

    override fun addShimmerEffect(it: String) {}

    override fun hideShimmerEffect(it: String) {}

    private fun doOnError(throwable: Throwable) {
        if (throwable.message != null)
       Snackbar.make(requireView(),throwable.message.toString(),Snackbar.LENGTH_LONG).show()
    }

    override fun doAction(action: Action) {
        if (action.actionsString == SHOW_LOADING){
            showLoading()
        }
        else if(action.actionsString == HIDE_LOADING){
            hideLoading()
        }
    }

    protected fun hideViewOnLoading(){
        toggleViewVisibility(false)
    }

    protected fun showViewAfterLoading(){
        toggleViewVisibility(true)
    }

    private fun toggleViewVisibility(shouldBeVisible : Boolean){
        updateViewVisibility(if (shouldBeVisible) View.VISIBLE else View.INVISIBLE)
    }

    protected open fun updateViewVisibility(visibility: Int){

    }

    override fun initRecycler() {
    }

    override fun initListeners() {
    }

    override fun swipeToRefreshListener() {}

    private fun showLoading(){
        if (activity != null) {
            dialog = LoadingUtil.showProgress(requireActivity())
            hideViewOnLoading()
        }
    }

    private fun hideLoading() {
        if (this::dialog.isInitialized){
            dialog.dismiss()
            showViewAfterLoading()
        }
    }

    protected fun hideKeyboardOnScroll(scrollView: View){
        scrollView.setOnScrollChangeListener { _, _, _, _, _ ->
            requireActivity().currentFocus?.let { hideKeyboardFrom(requireContext(), it) }
        }
    }

    companion object{
        const val SHOW_LOADING = "SHOW_LOADING"
        const val HIDE_LOADING = "HIDE_LOADING"
    }

}