package rs.ac.metropolitan.cs330_pz_4380.activities

import UserDBAdapter
import android.database.Cursor
import android.os.Bundle
import android.widget.TextView
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import rs.ac.metropolitan.cs330_pz_4380.R
import rs.ac.metropolitan.cs330_pz_4380.activities.fragments.add_pet.InsertPetDialogFragment
import rs.ac.metropolitan.cs330_pz_4380.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarHome.toolbar)

        binding.appBarHome.fab.setOnClickListener { _ ->
            val insertPetDialogFragment = InsertPetDialogFragment()
            insertPetDialogFragment.show(supportFragmentManager, insertPetDialogFragment.tag)
        }

        val intent = intent
        val userID = intent.getLongExtra("user_id", -1)

        val dbHelper = UserDBAdapter(this)
        dbHelper.open()

        val user = dbHelper.getUserById(userID)


        if (user.moveToFirst()) {
            val nameIndex = user.getColumnIndex(UserDBAdapter.KEY_NAME)
            val name = user.getString(nameIndex)

            val surnameIndex = user.getColumnIndex(UserDBAdapter.KEY_SURNAME)
            val surname = user.getString(surnameIndex)


            binding.navView.getHeaderView(0).findViewById<TextView>(R.id.name_surname).text = name + " " + surname

        }

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_home)
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }


    private fun getLoggedUser(): Cursor {
        val intent = intent

        val userID = intent.getLongExtra("user_id", -1)
        println(userID)
        val dbHelper = UserDBAdapter(this)
        dbHelper.open()

        return dbHelper.getUserById(userID)
    }

        override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_home)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}