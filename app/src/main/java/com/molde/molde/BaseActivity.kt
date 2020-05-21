package com.molde.molde

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

open class BaseActivity : AppCompatActivity() {

    fun initFragment(fragment: Fragment, tag: String, container: Int) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        val oldFragment = supportFragmentManager.findFragmentByTag(tag)

        if (oldFragment != null) {
            val oldFragmentTransaction = supportFragmentManager.beginTransaction()

            oldFragmentTransaction.remove(oldFragment)
            oldFragmentTransaction.commitAllowingStateLoss()
        }

        fragmentTransaction.replace(container, fragment, tag)
        fragmentTransaction.commitAllowingStateLoss()
    }

}