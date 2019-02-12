package com.leonkianoapps.leonk.dreamcatcher

import android.database.Cursor
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_dream_entry.*
import kotlinx.android.synthetic.main.activity_dream_view.*

class DreamViewActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dream_view)


        val extras = intent.extras

        if (extras != null) {

            val labelTapped = extras.getString("LABEL_NAME")

            //setting up toolbar
            setSupportActionBar(dream_view_toolbar)
            supportActionBar!!.title = labelTapped
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)

            loadDream(labelTapped)


        }else{

            //setting up toolbar
            setSupportActionBar(dream_toolBar)
            supportActionBar!!.title = "Something is wrong"
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        }




        //load data from db



    }

    private fun loadDream(labelTapped: String?) {


        val mDataBaseHelper  = DataBaseHelper(applicationContext)


        //first check if the name and email are already in the DB

        val dataLabel : Cursor = mDataBaseHelper.getDream(labelTapped)

        var returnedLabel  = ""

        //checking if name is already in the DB

        if(dataLabel != null){

            if (dataLabel.moveToFirst()){

                do {

                    returnedLabel = dataLabel.getString(2).toString()   //getting the name from the DB, the number indicates the column number

                    dreamTextView.text = returnedLabel

                }while ( dataLabel.moveToNext())

            }



            //Set TextView
            dreamTextView.text = returnedLabel


        }



    }



    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val id = item?.itemId

        if (id == android.R.id.home) {

            onBackPressed()  //go back to the previous activity

            return true
        }

        return super.onOptionsItemSelected(item)
    }
}
