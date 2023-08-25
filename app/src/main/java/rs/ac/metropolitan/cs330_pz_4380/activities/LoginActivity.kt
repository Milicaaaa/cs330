package rs.ac.metropolitan.cs330_pz_4380.activities

import UserDBAdapter
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import rs.ac.metropolitan.cs330_pz_4380.R


class LoginActivity : AppCompatActivity() {

    private lateinit var usernameEditText: EditText
    private lateinit var passwordEditText: EditText


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        usernameEditText = findViewById(R.id.etUsername)
        passwordEditText = findViewById(R.id.etPassword)

        val openActivityButton = findViewById<View>(R.id.login_btn)
        openActivityButton.setOnClickListener {
            val username = usernameEditText.text.toString()
            val password = passwordEditText.text.toString()

            loginUser(username, password)
        }

        val goBackActivity = findViewById<View>(R.id.back_button)
        goBackActivity.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun loginUser(username: String, password: String) {
        val dbHelper = UserDBAdapter(this)
        dbHelper.open()
        val cursor = dbHelper.getUserByUsernameAndPassword(username, password)

        if (cursor != null && cursor.moveToFirst()) {
            val userIdIndex = cursor.getColumnIndex(UserDBAdapter.KEY_ROWID)
            val userId = cursor.getLong(userIdIndex)

            val intent = Intent(this, HomeActivity::class.java)
            intent.putExtra("user_id", userId)
            startActivity(intent)
        } else {
            Toast.makeText(this, "Invalid username or password", Toast.LENGTH_SHORT).show()
        }

        cursor?.close()
        dbHelper.close()
    }

}