package com.example.basemodule.basemodule

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import com.example.basemodule.R
import com.google.android.material.snackbar.Snackbar
import java.lang.reflect.ParameterizedType

open class BaseActivity <T : BaseViewModel>: AppCompatActivity() {

    lateinit var viewModel: T

    @Suppress("UNCHECKED_CAST")
    private fun getViewModelClassType(): Class<T> {
        return (javaClass
            .genericSuperclass as ParameterizedType).actualTypeArguments[0] as Class<T>
    }

    private fun getViewModelForThisClass(): T {
        return ViewModelProvider(this).get(getViewModelClassType())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = getViewModelForThisClass()
    }
    var wholeScreen = -1

    var container = -1

    fun setContainerView(int : Int){
        container = int
    }

    fun setWholeScreenContainer(int : Int){
        wholeScreen = int
    }

    fun showSnackBar(view: View, message: String) {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG).show()
    }

    fun toast(message: String){
        Toast.makeText(this,message,Toast.LENGTH_LONG).show()
    }

    fun addFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .add(container, fragment, fragment.tag).addToBackStack("").commit()
    }

    fun addFragment(fragment: Fragment , wholeScreen : Boolean) {
        if (wholeScreen) {
            supportFragmentManager.beginTransaction()
                .setCustomAnimations(
                    R.anim.enter_from_right,
                    R.anim.exit_to_left,
                    R.anim.enter_from_left,
                    R.anim.exit_to_right
                )
                .add(this.wholeScreen, fragment, fragment.tag)
                .addToBackStack("")
                .commit()
        }else {
            supportFragmentManager.beginTransaction()
                .setCustomAnimations(
                    R.anim.enter_from_right,
                    R.anim.exit_to_left,
                    R.anim.enter_from_left,
                    R.anim.exit_to_right
                )
                .add(fragment, fragment.tag)
                .addToBackStack("")
                .commit()
        }
    }

    fun replaceFragment(fragment: Fragment, wholeScreen : Boolean) {
        if (wholeScreen){

            supportFragmentManager.beginTransaction()
                .setCustomAnimations(
                    R.anim.enter_from_right,
                    R.anim.exit_to_left,
                    R.anim.enter_from_left,
                    R.anim.exit_to_right
                )
                .replace(this.wholeScreen, fragment, fragment.tag).addToBackStack("").commit()
        }else{
            supportFragmentManager.beginTransaction()
                .setCustomAnimations(
                    R.anim.enter_from_right,
                    R.anim.exit_to_left,
                    R.anim.enter_from_left,
                    R.anim.exit_to_right
                )
                .replace(container, fragment, fragment.tag).addToBackStack("").commit()
        }
    }

    fun replaceFirstFragment(fragment : Fragment){
        supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        supportFragmentManager.beginTransaction()
            .replace(container, fragment, fragment.tag).addToBackStack("").commit()
    }


    override fun onBackPressed() {
        val fragmentCount = supportFragmentManager.backStackEntryCount
        if (fragmentCount == 1) {
            finish()
        } else {
            if (fragmentCount > 1) {
                supportFragmentManager.popBackStack()
            } else {
                super.onBackPressed()
            }
        }
    }

}