package com.leonkianoapps.leonk.dreamcatcher

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v4.view.ViewPager
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import kotlinx.android.synthetic.main.activity_intro_slide_show.*
import me.relex.circleindicator.CircleIndicator



class IntroSlideShowActivity : AppCompatActivity() {

    internal var circleIndicator: CircleIndicator? =null
    private var btnSkip: Button? = null
    private var btnNext:Button? = null
    private var introLayouts: IntArray? = null

    private var mUserIntro: Boolean? = false
    private val FIRST_TIME_INTRO = "first_time_intro"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)   //making it full screen
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        setContentView(R.layout.activity_intro_slide_show)

        introLayouts = intArrayOf(

            R.layout.intro1_layout,
            R.layout.intro2_layout,
            R.layout.intro3_layout

        )



        val adapter = IntroSlideShowAdapter(this)
        view_pager.adapter = adapter
        view_pager.addOnPageChangeListener(viewPagerPageChangeListener)

        circular_indicator!!.setViewPager(view_pager)


        val seenIntro = didUserSeeIntro()

        if (seenIntro == false) {

            //This activity will show
            markIntroSeen()

        } else {

            //The user already saw the intro
            launchHomeScreen()

        }


    }


    internal var viewPagerPageChangeListener: ViewPager.OnPageChangeListener = object : ViewPager.OnPageChangeListener {

        override fun onPageSelected(position: Int) {
            //                addBottomDots(position);

            // changing the next button text 'NEXT' / 'GOT IT'
            if (position == introLayouts!!.size - 1) {
                // last page. make button text to GOT IT
                btn_next!!.text = getString(R.string.start)
                btn_skip!!.visibility = View.GONE
            } else {
                // still pages are left
                btn_next!!.text = getString(R.string.next)
                btn_skip!!.visibility = View.VISIBLE
            }
        }

        override fun onPageScrolled(arg0: Int, arg1: Float, arg2: Int) {

        }

        override fun onPageScrollStateChanged(arg0: Int) {

        }
    }


    fun btnSkipClick(v: View) {
        launchHomeScreen()
    }


    fun btnNextClick(v: View) {
        // checking for last page
        // if last page home screen will be launched
        val current = getItem(1)
        if (current < introLayouts!!.size) {
            // move to next screen
            view_pager.currentItem = current
        } else {
            launchHomeScreen()
        }
    }
    private fun launchHomeScreen() {
        val intent = Intent(this, MainActivity::class.java);
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(intent)
        finish()    }

    private fun getItem(i: Int): Int {              //gets the current layout being seen
        return view_pager.currentItem + i
    }


    fun didUserSeeIntro(): Boolean? {   //Default the user has not seen the

        val sharedPreferencesIntro = PreferenceManager.getDefaultSharedPreferences(this)
        mUserIntro = sharedPreferencesIntro.getBoolean(FIRST_TIME_INTRO, false)
        return mUserIntro


    }

    private fun markIntroSeen() {

        val sharedPreferencesIntro = PreferenceManager.getDefaultSharedPreferences(this)
        mUserIntro = true
        sharedPreferencesIntro.edit().putBoolean(FIRST_TIME_INTRO, mUserIntro!!).apply()


    }

}
