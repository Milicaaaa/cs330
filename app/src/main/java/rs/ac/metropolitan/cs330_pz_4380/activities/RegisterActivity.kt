package rs.ac.metropolitan.cs330_pz_4380.activities

import UserDBAdapter
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import rs.ac.metropolitan.cs330_pz_4380.R

class RegisterActivity : AppCompatActivity(){

    var firstn: EditText? = null
    var sur: EditText? = null
    var usern: EditText? = null
    var pass: EditText? = null
    var regButton: Button? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        firstn = findViewById<View>(R.id.etFirstName) as EditText
        sur = findViewById<View>(R.id.etLastName) as EditText
        usern = findViewById<View>(R.id.etUsername) as EditText
        pass = findViewById<View>(R.id.etPassword) as EditText
        regButton = findViewById(R.id.reg_btn) as Button

        regButton!!.setOnClickListener {

            val name = firstn!!.text.toString().trimIndent()
            val surname = sur!!.text.toString()
            val username = usern!!.text.toString()
            val password = pass!!.text.toString()

            val dbHandler = UserDBAdapter(this@RegisterActivity)
            dbHandler.open()
            dbHandler.insertUser(name, surname, username, password)
            val intent = Intent(this@RegisterActivity, MainActivity::class.java)
            startActivity(intent)
            Toast.makeText(applicationContext, "Details Inserted Successfully", Toast.LENGTH_SHORT)
                .show()
        }

        val openActivityButton = findViewById<View>(R.id.back_button)
        openActivityButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }


    }
}