package com.leonkianoapps.leonk.dreamcatcher

import android.content.Intent
import android.database.Cursor
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val labelInfoFromDB: ArrayList<UserInfo> = ArrayList()  //To store user information from the DB


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //setting up toolbar
        setSupportActionBar(main_toolBar)


        //get data from DB
        getDataFromDB()

//Setting up listView

        usersListView.adapter = ListViewCustomAdapter(applicationContext, labelInfoFromDB)
        usersListView.setOnItemClickListener { adapterView, view, position, l ->


            val labelTapped = labelInfoFromDB[position].label   //getting name from the arrayList based on the position

            //openActivity

//            val intent = Intent(applicationContext, DreamViewActivity::class.java)
//            intent.putExtra("LABEL_NAME", labelTapped)
//            startActivity(intent)
//

//            val value = labelTapped
            val i = Intent(this@MainActivity, DreamViewActivity::class.java)
            i.putExtra("LABEL_NAME", labelTapped)
            startActivity(i)


        }

        //checking to see if there is data stored if not displaying message

        if (labelInfoFromDB.isEmpty()) {
            infoTextView.visibility = View.VISIBLE
            logoImageView.visibility = View.VISIBLE
        } else {
            infoTextView.visibility = View.INVISIBLE

            logoImageView.visibility = View.INVISIBLE

        }


    }

    private fun getDataFromDB() {
        val mDataBaseHelper = DataBaseHelper(applicationContext)


        val allData: Cursor = mDataBaseHelper.data


        if (allData != null) {

            if (allData.moveToFirst()) {

                do {
//

                    val labelFromDB = allData.getString(1)  //getting label

                    val enterUser = UserInfo(labelFromDB)

                    labelInfoFromDB.add(enterUser)   //adding it to the arrayList

                } while (allData.moveToNext())

            }

        }
    }


    fun addDream(v: View) {

        //open activity to add dream
        val intent = Intent(this, DreamEntryActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(intent)
        finish()
    }


}


