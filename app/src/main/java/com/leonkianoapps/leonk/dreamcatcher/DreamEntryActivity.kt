package com.leonkianoapps.leonk.dreamcatcher

import android.content.Intent
import android.database.Cursor
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_dream_entry.*

class DreamEntryActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dream_entry)


        //setting up toolbar
        setSupportActionBar(dream_toolBar)
        supportActionBar!!.title = "Enter Your Dream"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)


        submitDream.setOnClickListener {

            submitTheDream()
        }

    }

    private fun submitTheDream() {

        //checking validity

        if (TextUtils.isEmpty(label_TextField.text.toString())) {    //if name is empty show error message

            label_TextInputLayout.error = "Please Enter a label"

            return //to stop the method here

        }

        if (TextUtils.isEmpty(infor_dream.text.toString())) {   //if email is empty show error message


            Toast.makeText(applicationContext,"Please enter your Dream",Toast.LENGTH_LONG).show()

            return //to stop the method here

        }

        //Here information is valid/Good insert data to DB

        val label = label_TextField.text.toString()
        val dream = dreamEditText.text.toString()

        insertDataToDB(label,dream)



    }

    private fun insertDataToDB(label: String, dream: String) {

        val mDataBaseHelper  = DataBaseHelper(applicationContext)


        //first check if the name and email are already in the DB

        val dataLabel : Cursor = mDataBaseHelper.getLabel(label)
        val dataDream : Cursor = mDataBaseHelper.getDream(dream)

        var returnedLabel  = ""
        var returnedDream  = ""

        //checking if name is already in the DB

        if(dataLabel != null){

            if (dataLabel.moveToFirst()){

                do {

                    returnedLabel = dataLabel.getString(1).toString()   //getting the name from the DB, the number indicates the column number

                }while ( dataLabel.moveToNext())

            }

            if (returnedLabel.isNotEmpty()){  //means name is already in the DB

                Toast.makeText(applicationContext,"Label is already used",Toast.LENGTH_LONG).show()


                return // to stop the method here

            }

        }

        //checking if email is already in the DB

        if(dataDream != null){

            if (dataDream.moveToFirst()){

                do {

                    returnedDream = dataDream.getString(2).toString()  //getting the email,the number,indicates the column number

                }while ( dataDream.moveToNext())

            }

            if (returnedDream.isNotEmpty()){  //means name is already in the DB

                Toast.makeText(applicationContext,"Dream is already used",Toast.LENGTH_LONG).show()

                return // to stop the method here

            }

        }



        //From here everything is good



        val insertData : Boolean = mDataBaseHelper.addData(label,dream)      //calling java from kotlin

        if (insertData) {

            //Data was entered Successfully
            val intent = Intent(applicationContext, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)

            Toast.makeText(applicationContext,"Dream Entered Success!",Toast.LENGTH_LONG).show()

            //log to see if info was stored in DB correctly

            val allData : Cursor = mDataBaseHelper.data

            if (allData!=null){

                if (allData.moveToFirst()){

                    do{

                        val label = allData.getString(1)
                        val dream =allData.getString(2)

//                        Log.i("NAME and EMAIL from DB ","$label $dream")

                    }while (allData.moveToNext())

                }

            }

        }//end of if insert Data


    }


    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val id = item?.itemId

        if (id == android.R.id.home) {

            onBackPressed()  //go back to the previous activity

            return true
        }

        return super.onOptionsItemSelected(item)
    }


//    override fun onBackPressed() {
//
//
//        super.onBackPressed()
//    }
}
