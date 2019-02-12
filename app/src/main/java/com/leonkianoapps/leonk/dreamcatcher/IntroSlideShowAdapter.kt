package com.leonkianoapps.leonk.dreamcatcher

import android.content.Context
import android.support.v4.view.PagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class IntroSlideShowAdapter(
    private val context: Context   //Named private to be only used in the slideShow adapter class
) : PagerAdapter() {
    lateinit var inflater: LayoutInflater

    var introLayouts = intArrayOf(

        R.layout.intro1_layout,
        R.layout.intro2_layout,
        R.layout.intro3_layout

    )


    override fun getCount(): Int {
        return introLayouts.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`


    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {

        inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        val view = inflater.inflate(introLayouts[position], container, false)

        container.addView(view)

        return view

    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {

        val view = `object` as View
        container.removeView(view)

    }


}